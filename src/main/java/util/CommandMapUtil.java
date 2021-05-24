package util;


import models.enums.MapType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.GlobalConstants.*;

public class CommandMapUtil {
    private static final Set<String> VALID_COMMANDS = Set.of(
            CONFIG_CMD, RUN_CMD,
            SUITE_CMD, TEST_CMD,
            CONFIG_CMD_SHORT, RUN_CMD_SHORT,
            SUITE_CMD_SHORT, TEST_CMD_SHORT
    );

    private static final Set<String> ENVIRONMENT_COMMANDS = Set.of(
            SERVER_CMD, DEBUG_CMD,
            SERVER_CMD_SHORT, DEBUG_CMD_SHORT
    );

    public static Map<String, String> getCommandsMap(String[] args, MapType type) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2) {
            String cmd = args[i];
            String value = args[i + 1];
            String key;
            if (type == MapType.RUN) {
                if (VALID_COMMANDS.contains(cmd)) {
                    switch (cmd) {
                        case CONFIG_CMD_SHORT:
                            key = CONFIG_CMD;
                            map.put(key, value);
                            break;
                        case RUN_CMD_SHORT:
                            key = RUN_CMD;
                            map.put(key, value);
                            break;
                        case SUITE_CMD_SHORT:
                            key = SUITE_CMD;
                            map.put(key, value);
                            break;
                        case TEST_CMD_SHORT:
                            key = TEST_CMD;
                            map.put(key, value);
                            break;
                        default:
                            map.put(cmd, value);
                    }
                }
            } else {
                if (ENVIRONMENT_COMMANDS.contains(cmd)) {
                    switch (cmd) {
                        case SERVER_CMD_SHORT:
                            key = SERVER_CMD;
                            map.put(key, value);
                            break;
                        case DEBUG_CMD_SHORT:
                            key = DEBUG_CMD;
                            map.put(key, value);
                            break;
                        default:
                            map.put(cmd, value);
                    }
                }
            }
        }
        return map;
    }
}
