package com.riserdisk.dynamicservericon.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.riserdisk.dynamicservericon.Dynamicservericon;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class DynamicServerIconCommand {

    private DynamicServerIconCommand() {

    }

    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher
    ) {

        dispatcher.register(

                CommandManager.literal("dynamicservericon")

                        .requires(source -> source.hasPermissionLevel(4))

                        .executes(context -> {

                            sendHelp(context.getSource());

                            return 1;

                        })

                        .then(

                                CommandManager.literal("reload")

                                        .executes(context -> {

                                            Dynamicservericon.reloadConfiguration();

                                            context.getSource().sendFeedback(
                                                    () -> Text.literal(
                                                            "DynamicServerIcon configuration reloaded."
                                                    ),
                                                    true
                                            );

                                            return 1;

                                        })

                        )

                        .then(

                                CommandManager.literal("interval")

                                        .then(

                                                CommandManager.argument(
                                                        "seconds",
                                                        IntegerArgumentType.integer(1)
                                                )

                                                        .executes(context -> {

                                                            int seconds = IntegerArgumentType.getInteger(
                                                                    context,
                                                                    "seconds"
                                                            );

                                                            Dynamicservericon.CONFIG_MANAGER
                                                                    .setRotationInterval(seconds);

                                                            Dynamicservericon.ICON_MANAGER
                                                                    .setRotationInterval(seconds);

                                                            context.getSource().sendFeedback(
                                                                    () -> Text.literal(
                                                                            "Rotation interval changed to "
                                                                                    + seconds
                                                                                    + " second(s)."
                                                                    ),
                                                                    true
                                                            );

                                                            return 1;

                                                        })

                                        )

                        )

        );

    }

    private static void sendHelp(ServerCommandSource source) {

        source.sendFeedback(
                () -> Text.literal("""

                        DynamicServerIcon

                        Available commands:
                        /dynamicservericon reload
                        /dynamicservericon interval <seconds>
                        /dynamicservericon status
                        """),
                false
        );

    }

}