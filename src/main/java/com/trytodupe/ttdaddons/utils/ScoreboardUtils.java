package com.trytodupe.ttdaddons.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.StringUtils;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class ScoreboardUtils {
    public static String cleanSB(String scoreboard) {
        char[] nvString = StringUtils.stripControlCodes(scoreboard).toCharArray();
        StringBuilder cleaned = new StringBuilder();
        for (char c : nvString) {
            if (c > '\024' && c < '')
                cleaned.append(c);
        }
        return cleaned.toString();
    }

    public static List<String> getScoreboard() {
        List<String> lines = new ArrayList<>();
        if (mc.theWorld == null)
            return lines;
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null)
            return lines;
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null)
            return lines;
        Collection<Score> scores = scoreboard.getSortedScores(objective);
        List<Score> list = (List<Score>)scores.stream().filter(input -> (input != null && input.getPlayerName() != null && !input.getPlayerName().startsWith("#"))).collect(Collectors.toList());
        if (list.size() > 15) {
            scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
        } else {
            scores = list;
        }
        for (Score score : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
        }
        return lines;
    }

    public static boolean scoreboardContains(String string) {
        boolean result = false;
        List<String> scoreboard = getScoreboard();
        for (String line : scoreboard) {
            line = cleanSB(line);
            line = StringUtils.stripControlCodes(line);
            if (line.contains(string)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static String getLineThatContains(String string) {
        for (String line : getScoreboard()) {
            line = cleanSB(line);
            if (line.contains(string))
                return line;
        }
        return null;
    }
}
