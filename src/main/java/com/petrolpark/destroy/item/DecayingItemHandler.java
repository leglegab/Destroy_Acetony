package com.petrolpark.destroy.item;

@FunctionalInterface
public interface DecayingItemHandler {
    
    public long getGameTime();

    public static class ServerDecayingItemHandler implements DecayingItemHandler {
        
        public long gameTime;

        @Override
        public long getGameTime() {
            return gameTime;
        };

    };
};
