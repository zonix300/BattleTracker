package com.zonix.dndapp.resolver;

import com.zonix.dndapp.entity.*;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class LobbyRequestResolverRegistry {

    private final Map<LobbyRequestType, RequestResolver> resolvers = new EnumMap<>(LobbyRequestType.class);

    public LobbyRequestResolverRegistry(List<RequestResolver> resolverList) {
        resolverList.forEach(resolver -> {
            if (resolver instanceof ConnectRequestResolver) {
                resolvers.put(LobbyRequestType.CONNECT, resolver);
            } else if (resolver instanceof HealRequestResolver) {
                resolvers.put(LobbyRequestType.HEAL, resolver);
            } else if (resolver instanceof DamageRequestResolver){
                resolvers.put(LobbyRequestType.DAMAGE, resolver);
            }
        });
    }

    public void apply(LobbyRequest request, Lobby lobby, User user) {
        RequestResolver resolver = resolvers.get(request.getType());
        if (resolver == null) {
            throw new UnsupportedOperationException("No resolver for type: " + request.getType());
        }

        if (request.getStatus() == LobbyRequestStatus.ACCEPT) {
            resolver.apply(request, lobby, user);
        }
    }
}
