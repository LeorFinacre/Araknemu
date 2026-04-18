package fr.quatrevieux.araknemu.game.admin.player;

import fr.quatrevieux.araknemu.common.account.Permission;
import fr.quatrevieux.araknemu.game.admin.AbstractCommand;
import fr.quatrevieux.araknemu.game.admin.AdminPerformer;
import fr.quatrevieux.araknemu.game.admin.exception.AdminException;
import fr.quatrevieux.araknemu.game.player.GamePlayer;

public final class MapInfo extends AbstractCommand<Void> {
    GamePlayer player;

    public MapInfo(GamePlayer player) {
        this.player = player;
    }

    @Override
    protected void build(Builder builder) {
        builder
                .help(
                        formatter -> formatter
                                .description("Get info of the current map")
                                .synopsis("mapinfo")
                                .example("mapinfo", "Get the info of the map")
                )
                .requires(Permission.MANAGE_PLAYER)
        ;
    }

    @Override
    public String name() {
        return "mapinfo";
    }

    @Override
    public void execute(AdminPerformer performer, Void arguments) throws AdminException {
        performer.success("MapID {} CellID {}", player.position().map(), player.position().cell());
    }
}