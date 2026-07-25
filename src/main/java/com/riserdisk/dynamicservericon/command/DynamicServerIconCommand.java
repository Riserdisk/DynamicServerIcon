package com.riserdisk.dynamicservericon.command;

import com.mojang.brigadier.CommandDispatcher;
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