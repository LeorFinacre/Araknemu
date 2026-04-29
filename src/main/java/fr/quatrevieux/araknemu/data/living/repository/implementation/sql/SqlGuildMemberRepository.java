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
import fr.quatrevieux.araknemu.data.living.entity.social.GuildMember;
import fr.quatrevieux.araknemu.data.living.repository.social.guild.GuildMemberRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlGuildMemberRepository implements GuildMemberRepository {
    private final QueryExecutor executor;
    private final RepositoryUtils<GuildMember> utils;

    public SqlGuildMemberRepository(QueryExecutor executor) {
        this.executor = executor;
        this.utils = new RepositoryUtils<>(this.executor, new SqlGuildMemberRepository.Loader());
    }

    @Override
    public GuildMember add(GuildMember entity) throws RepositoryException {
        utils.update(
                "REPLACE INTO GUILD_MEMBER (`PLAYER_ID`, `GUILD_ID`, `RANK`, `GIVEN_XP`, `RIGHTS`, `ALIGN`) VALUES (?, ?, ?, ?, ?, ?)",
                rs -> {
                    rs.setInt(1, entity.getPlayerId());
                    rs.setInt(2, entity.getGuildId());
                    rs.setInt(3, entity.getRank());
                    rs.setInt(4, entity.getGivenXp());
                    rs.setInt(5, entity.getRights());
                    rs.setInt(6, entity.getAlign());
                }
        );

        return entity;
    }

    @Override
    public void delete(GuildMember entity) throws RepositoryException {
        final int count = utils.update(
                "DELETE FROM GUILD_MEMBER WHERE PLAYER_ID = ? AND GUILD_ID = ?", rs -> {
                    rs.setInt(1, entity.getPlayerId());
                    rs.setInt(2, entity.getGuildId());
                });

        if (count != 1) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void initialize() throws RepositoryException {
        try {
            executor.query(
                    "CREATE TABLE GUILD_MEMBER (" +
                            "PLAYER_ID INTEGER," +
                            "GUILD_ID INTEGER," +
                            "RANK INTEGER," +
                            "GIVEN_XP BIG_INT" +
                            "RIGHTS BIG_INT" +
                            "ALIGN TINY_INT" +
                            "UNIQUE (PLAYER_ID, GUILD_ID)" +
                            ")"
            );
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void destroy() throws RepositoryException {
        try {
            executor.query("DROP TABLE GUILD_MEMBER");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public GuildMember get(GuildMember entity) throws RepositoryException {
        try {
            return utils.findOne("SELECT * FROM GUILD_MEMBER WHERE PLAYER_ID = ? AND GUILD_ID = ?", rs -> {
                rs.setInt(1, entity.getPlayerId());
                rs.setInt(2, entity.getGuildId());
            });
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean has(GuildMember entity) throws RepositoryException {
        return utils.aggregate("SELECT COUNT(*) FROM GUILD_MEMBER WHERE GUILD_ID = ?", rs -> {
            rs.setInt(1, entity.getGuildId());
        }) > 0;
    }

    @Override
    public GuildMember getGuildIdByPlayerId(int playerId) {
        try {
            return utils.findOne("SELECT * FROM GUILD_MEMBER WHERE PLAYER_ID = ?", rs -> {
                rs.setInt(1, playerId);
            });
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<GuildMember> findByGuild(int guildId) {
        try {
            return utils.findAll("SELECT * FROM GUILD_MEMBER WHERE GUILD_ID = ?", rs -> {
                rs.setInt(1, guildId);
            });
        } catch (EntityNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    private static class Loader implements RepositoryUtils.Loader<GuildMember> {
        @Override
        public GuildMember create(Record record) throws SQLException {
            return new GuildMember(
                    record.getInt("PLAYER_ID"),
                    record.getInt("GUILD_ID"),
                    record.getInt("RANK"),
                    record.getInt("GIVEN_XP"),
                    record.getInt("RIGHTS"),
                    record.getInt("ALIGN")
            );
        }

        @Override
        public GuildMember fillKeys(GuildMember entity, ResultSet keys) throws SQLException {
            throw new UnsupportedOperationException();
        }
    }
}
