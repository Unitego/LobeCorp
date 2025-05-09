package net.unitego.lobecorp.common.item.badge;

import com.mojang.brigadier.ParseResults;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;

import java.util.Objects;

public class TeamBadge extends Item implements LobeCorpSlotAccess {
    private final LobeCorpTeam team;

    public TeamBadge(Properties properties, LobeCorpTeam team) {
        super(properties);
        this.team = team;
    }

    //执行命令通用方法
    public static void executeCommand(Commands commands, CommandSourceStack commandSourceStack, String command) {
        ParseResults<CommandSourceStack> parseResults = commands.getDispatcher().parse(command, commandSourceStack.withSuppressedOutput());
        commands.performCommand(parseResults, command);
    }

    //部门同步
    public static void syncTeam(ItemStack itemStack, ServerPlayer serverPlayer) {
        Commands commands = Objects.requireNonNull(serverPlayer.getServer()).getCommands();
        CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack();
        if (itemStack.getItem() instanceof TeamBadge teamBadge) {
            executeCommand(commands, commandSourceStack, "team join " + teamBadge.team.getTeamName() + " " + serverPlayer.getName().getString());
        } else if (itemStack.isEmpty()) {
            executeCommand(commands, commandSourceStack, "team leave " + serverPlayer.getName().getString());
        }
    }

    //创建部门
    public static void createTeam(Commands commands, CommandSourceStack commandSourceStack) {
        for (LobeCorpTeam team : LobeCorpTeam.values()) {
            //创建部门
            TeamBadge.executeCommand(commands, commandSourceStack, "team add " + team.getTeamName());
            //设置部门前缀及其颜色
            String command = String.format("team modify %s prefix {\"translate\":\"%s\",\"color\":\"%s\"}",
                    team.getTeamName(), teamBadgeString(team.getTeamName()), team.getTeamColor().getName().toLowerCase());
            TeamBadge.executeCommand(commands, commandSourceStack, command);
        }
    }

    public static String teamBadgeString(String name) {
        return LobeCorp.MOD_ID + ".team." + name;
    }

    @Override
    public LobeCorpEquipmentSlot getLobeCorpSlot() {
        return LobeCorpEquipmentSlot.LOBECORP_BADGE;
    }

    //脑叶公司部门
    public enum LobeCorpTeam {
        CONTROL_TEAM("control_team", ChatFormatting.DARK_RED),//控制部
        INFORMATION_TEAM("information_team", ChatFormatting.DARK_PURPLE),//情报部
        SECURITY_TEAM("security_team", ChatFormatting.DARK_GREEN),//安保部
        TRAINING_TEAM("training_team", ChatFormatting.GOLD),//培训部
        CENTRAL_COMMAND_TEAM("central_command_team", ChatFormatting.YELLOW),//中央本部
        WELFARE_TEAM("welfare_team", ChatFormatting.BLUE),//福利部
        DISCIPLINARY_TEAM("disciplinary_team", ChatFormatting.RED),//惩戒部
        RECORD_TEAM("record_team", ChatFormatting.DARK_GRAY),//记录部
        EXTRACTION_TEAM("extraction_team", ChatFormatting.BLACK),//研发部
        ARCHITECTURE_TEAM("architecture_team", ChatFormatting.WHITE);//构筑部

        private final String teamName;
        private final ChatFormatting teamColor;

        LobeCorpTeam(String teamName, ChatFormatting teamColor) {
            this.teamName = teamName;
            this.teamColor = teamColor;
        }

        public String getTeamName() {
            return teamName;
        }

        public ChatFormatting getTeamColor() {
            return teamColor;
        }
    }
}
