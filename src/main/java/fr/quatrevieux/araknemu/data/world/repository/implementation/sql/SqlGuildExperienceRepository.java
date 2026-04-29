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

package fr.quatrevieux.araknemu.data.world.repository.implementation.sql;

import fr.quatrevieux.araknemu.core.dbal.executor.QueryExecutor;
import fr.quatrevieux.araknemu.core.dbal.repository.Record;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryException;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryUtils;
import fr.quatrevieux.araknemu.data.world.entity.guild.GuildExperience;
import fr.quatrevieux.araknemu.data.world.repository.guild.GuildExperienceRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL implementation of the repository
 */
final class SqlGuildExperienceRepository implements GuildExperienceRepository {
    private final QueryExecutor executor;
    private final RepositoryUtils<GuildExperience> utils;

    public SqlGuildExperienceRepository(QueryExecutor executor) {
        this.executor = executor;
        utils = new RepositoryUtils<>(executor, new Loader());
    }

    @Override
    public void initialize() throws RepositoryException {
        try {
            executor.query(
                    "CREATE TABLE GUILD_XP (" +
                            "GUILD_LEVEL SMALLINT PRIMARY KEY," +
                            "EXPERIENCE BIGINT" +
                            ")"
            );
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void destroy() throws RepositoryException {
        try {
            executor.query("DROP TABLE GUILD_XP");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public GuildExperience get(GuildExperience entity) throws RepositoryException {
        return utils.findOne(
                "SELECT * FROM GUILD_XP WHERE GUILD_LEVEL = ?",
                stmt -> stmt.setInt(1, entity.level())
        );
    }

    @Override
    public boolean has(GuildExperience entity) throws RepositoryException {
        return utils.aggregate(
                "SELECT COUNT(*) FROM GUILD_XP WHERE GUILD_LEVEL = ?",
                stmt -> stmt.setInt(1, entity.level())
        ) > 0;
    }

    private static class Loader implements RepositoryUtils.Loader<GuildExperience> {
        @Override
        public GuildExperience create(Record record) throws SQLException {
            return new GuildExperience(
                    record.getPositiveInt("GUILD_LEVEL"),
                    record.getNonNegativeLong("EXPERIENCE")
            );
        }

        @Override
        public GuildExperience fillKeys(GuildExperience entity, ResultSet keys) {
            throw new RepositoryException("Read-only entity");
        }
    }
}
