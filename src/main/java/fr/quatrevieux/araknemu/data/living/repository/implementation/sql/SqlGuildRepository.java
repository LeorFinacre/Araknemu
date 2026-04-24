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

package fr.quatrevieux.araknemu.data.living.repository.implementation.sql;

import fr.quatrevieux.araknemu.core.dbal.executor.QueryExecutor;
import fr.quatrevieux.araknemu.core.dbal.repository.EntityNotFoundException;
import fr.quatrevieux.araknemu.core.dbal.repository.Record;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryException;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryUtils;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.data.living.entity.social.Guild;
import fr.quatrevieux.araknemu.data.living.repository.social.guild.GuildRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlGuildRepository implements GuildRepository {
    private final QueryExecutor executor;
    private final RepositoryUtils<Guild> utils;

    public SqlGuildRepository(QueryExecutor executor) {
        this.executor = executor;
        this.utils = new RepositoryUtils<>(this.executor, new SqlGuildRepository.Loader());
    }

    @Override
    public Guild add(Guild entity) throws RepositoryException {
        utils.update(
                "REPLACE INTO GUILDS (`NAME`, `EMBLEM`) VALUES (?, ?)",
                rs -> {
                    rs.setString(1, entity.getName());
                    rs.setString(2, entity.getEmblem());
                }
        );

        return entity;
    }

    @Override
    public void delete(Guild entity) {
        final int count = utils.update(
                "DELETE FROM GUILDS WHERE ID = ?", rs -> {
                    rs.setInt(1, entity.getId());
                });

        if (count != 1) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void initialize() throws RepositoryException {
        try {
            executor.query(
                    "CREATE TABLE GUILDS (" +
                            "ID INTEGER PRIMARY KEY," +
                            "NAME VARCHAR(50)," +
                            "EMBLEM VARCHAR(25)," +
                            "LEVEL INT" +
                            "EXPERIENCE BIG_INT" +
                            "CAPITAL TINT" +
                            "PERCO_MAX INT" +
                            "SPELLS VARCHAR(255)" +
                            "STATS VARCHAR(255)" +
                            "UNIQUE (ID, NAME, EMBLEM)" +
                            ")"
            );
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void destroy() throws RepositoryException {
        try {
            executor.query("DROP TABLE GUILDS");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Guild get(Guild entity) throws RepositoryException {
        try {
            return utils.findOne("SELECT * FROM GUILDS WHERE ID = ?", rs -> {
                rs.setInt(1, entity.getId());
            });
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean has(Guild entity) throws RepositoryException {
        return utils.aggregate("SELECT COUNT(*) FROM GUILDS WHERE ID = ?", rs -> {
            rs.setInt(1, entity.getId());
        }) > 0;
    }

    @Override
    public Guild getGuildById(int guildId) {
        try {
            return utils.findOne("SELECT * FROM GUILDS WHERE ID = ?", rs -> {
                rs.setInt(1, guildId);
            });
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    private static class Loader implements RepositoryUtils.Loader<Guild> {
        @Override
        public Guild create(Record record) throws SQLException {
            return new Guild(
                    record.getInt("ID"),
                    record.getString("NAME"),
                    record.getString("EMBLEM"),
                    record.getInt("LEVEL"),
                    record.getInt("EXPERIENCE"),
                    record.getInt("CAPITAL"),
                    record.getInt("PERCO_MAX"),
                    record.getString("SPELLS"),
                    record.getString("STATS")
            );
        }

        @Override
        public Guild fillKeys(Guild entity, ResultSet keys) {
            throw new UnsupportedOperationException();
        }
    }
}
