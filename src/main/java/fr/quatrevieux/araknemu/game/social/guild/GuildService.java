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

package fr.quatrevieux.araknemu.game.social.guild;

import fr.quatrevieux.araknemu.core.event.EventsSubscriber;
import fr.quatrevieux.araknemu.core.event.Listener;
import fr.quatrevieux.araknemu.data.living.entity.player.Player;
import fr.quatrevieux.araknemu.data.living.entity.social.PlayerGuild;
import fr.quatrevieux.araknemu.data.living.repository.player.PlayerRepository;
import fr.quatrevieux.araknemu.data.living.repository.social.guild.GuildMemberRepository;
import fr.quatrevieux.araknemu.data.living.repository.social.guild.GuildRepository;
import fr.quatrevieux.araknemu.game.PreloadableService;
import fr.quatrevieux.araknemu.game.handler.event.Disconnected;
import fr.quatrevieux.araknemu.game.listener.player.SendGuildStats;
import fr.quatrevieux.araknemu.game.player.GamePlayer;
import fr.quatrevieux.araknemu.game.player.event.PlayerLoaded;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GuildService implements PreloadableService, EventsSubscriber {
    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final PlayerRepository playerRepository;

    private final Map<Integer, GameGuild> guilds = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> playerGuildIndex = new ConcurrentHashMap<>();

    public GuildService(GuildRepository guildRepository, GuildMemberRepository guildMemberRepository, PlayerRepository playerRepository) {
        this.guildRepository = guildRepository;
        this.guildMemberRepository = guildMemberRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Listener[] listeners() {
        return new Listener[] {
            new Listener<PlayerLoaded>() {
                @Override
                public void on(PlayerLoaded event) {
                    GamePlayer player = event.player();
                    int guildId = playerGuildIndex.getOrDefault(player.id(), 0);
                    if (guildId > 0) {
                        GameGuild guild = guilds.get(guildId);
                        if (guild != null) {
                            GameGuildMember member = guild.member(player.id());
                            if (member != null) {
                                member.attach(player);
                            }
                        }
                    }
                    event.player().dispatcher().add(new SendGuildStats(event.player()));
                }

                @Override
                public Class<PlayerLoaded> event() {
                    return PlayerLoaded.class;
                }
            },

            new Listener<Disconnected>() {
                @Override
                public void on(Disconnected event) {
                }

                @Override
                public Class<Disconnected> event() {
                    return Disconnected.class;
                }
            }
        };
    }

    @Override
    public void preload(Logger logger) {
        logger.info("Loading guilds...");

        for (PlayerGuild guildEntity : guildRepository.load()) {
            GameGuild gameGuild = create(guildEntity);
            guilds.put(guildEntity.getId(), gameGuild);
        }

        logger.info("{} guilds and {} members indexed", guilds.size(), playerGuildIndex.size());
    }

    /**
     * Récupère la guilde d'un joueur via son ID de personnage
     */
    public @Nullable GameGuild getByPlayerId(int playerId) {
        @Nullable Integer guildId = playerGuildIndex.get(playerId);

        if (guildId == null) {
            return null;
        }

        return guilds.get(guildId);
    }

    @Override
    public String name() {
        return "social.guild";
    }

    /**
     * Get all the guilds loaded
     */
    public Collection<GameGuild> all() {
        return guilds.values();
    }

    public void detach(GamePlayer player) {
        if(player.getGuild() != null) {
            GameGuildMember member = player.getGuild().member(player.id());
            if(member != null)
                member.detach();
        }
    }

    /**
     * Creates the GameGuild game object from the entity and its members
     */
    private GameGuild create(PlayerGuild entity) {
        List<GameGuildMember> members = guildMemberRepository.findByGuild(entity.getId())
                .stream()
                .map(member -> {
                    Player player = playerRepository.findById(member.getPlayerId());
                    playerGuildIndex.put(player.id(), entity.getId());
                    return new GameGuildMember(
                            member,
                            player
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new GameGuild(entity, members);
    }
}
