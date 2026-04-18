/*
 * This file is part of Araknemu.
 *
 * Araknemu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Araknemu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Araknemu.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2017-2026 Leor Finacre
 */
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
