package io.github.amerebagatelle.mods.nuit.skybox;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.amerebagatelle.mods.nuit.util.CodecUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Properties {
    public static final Codec<Properties> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("priority", 0).forGetter(Properties::getPriority),
            Fade.CODEC.fieldOf("fade").forGetter(Properties::getFade),
            CodecUtils.getClampedInteger(1, Integer.MAX_VALUE).optionalFieldOf("transitionInDuration", 20).forGetter(Properties::getTransitionInDuration),
            CodecUtils.getClampedInteger(1, Integer.MAX_VALUE).optionalFieldOf("transitionOutDuration", 20).forGetter(Properties::getTransitionOutDuration),
            Codec.BOOL.optionalFieldOf("changeFog", false).forGetter(Properties::isChangeFog),
            Codec.BOOL.optionalFieldOf("changeFogDensity", false).forGetter(Properties::isChangeFogDensity),
            RGBA.CODEC.optionalFieldOf("fogColors", RGBA.DEFAULT).forGetter(Properties::getFogColors),
            Codec.BOOL.optionalFieldOf("sunSkyTint", true).forGetter(Properties::isRenderSunSkyTint),
            Codec.BOOL.optionalFieldOf("inThickFog", true).forGetter(Properties::isRenderInThickFog),
            Rotation.CODEC.optionalFieldOf("rotation", Rotation.DEFAULT).forGetter(Properties::getRotation)
    ).apply(instance, Properties::new));

    public static final Properties DEFAULT = new Properties(0, Fade.DEFAULT, 20, 20, false, false, RGBA.DEFAULT, true, true, Rotation.DEFAULT);

    private final int priority;
    private final Fade fade;
    private final int transitionInDuration;
    private final int transitionOutDuration;
    private final boolean changeFog;
    private final boolean changeFogDensity;
    private final RGBA fogColors;
    private final boolean renderSunSkyTint;
    private final boolean renderInThickFog;
    private final Rotation rotation;

    public Properties(int priority, Fade fade, int transitionInDuration, int transitionOutDuration, boolean changeFog, boolean changeFogDensity, RGBA fogColors, boolean renderSunSkyTint, boolean renderInThickFog, Rotation rotation) {
        this.priority = priority;
        this.fade = fade;
        this.transitionInDuration = transitionInDuration;
        this.transitionOutDuration = transitionOutDuration;
        this.changeFog = changeFog;
        this.changeFogDensity = changeFogDensity;
        this.fogColors = fogColors;
        this.renderSunSkyTint = renderSunSkyTint;
        this.renderInThickFog = renderInThickFog;
        this.rotation = rotation;
    }

    public int getPriority() {
        return priority;
    }

    public Fade getFade() {
        return this.fade;
    }

    public int getTransitionInDuration() {
        return transitionInDuration;
    }

    public int getTransitionOutDuration() {
        return transitionOutDuration;
    }

    public boolean isChangeFog() {
        return this.changeFog;
    }

    public boolean isChangeFogDensity() {
        return changeFogDensity;
    }

    public RGBA getFogColors() {
        return this.fogColors;
    }

    public boolean isRenderSunSkyTint() {
        return renderSunSkyTint;
    }

    public boolean isRenderInThickFog() {
        return renderInThickFog;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Builder {
        private int priority = 0;
        private Fade fade = Fade.DEFAULT;
        private float minAlpha = 0F;
        private float maxAlpha = 1F;
        private int transitionInDuration = 20;
        private int transitionOutDuration = 20;
        private boolean changeFog = false;
        private boolean changeFogDensity = false;
        private RGBA fogColors = RGBA.DEFAULT;
        private boolean renderSunSkyTint = true;
        private boolean renderInTickFog = true;
        private Rotation rotation = Rotation.DEFAULT;

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder fade(Fade fade) {
            this.fade = fade;
            return this;
        }

        public Builder minAlpha(float minAlpha) {
            this.minAlpha = minAlpha;
            return this;
        }

        public Builder maxAlpha(float maxAlpha) {
            this.maxAlpha = maxAlpha;
            return this;
        }

        public Builder transitionInDuration(int transitionInDuration) {
            this.transitionInDuration = transitionInDuration;
            return this;
        }

        public Builder transitionOutDuration(int transitionOutDuration) {
            this.transitionOutDuration = transitionOutDuration;
            return this;
        }

        public Builder changesFog() {
            this.changeFog = true;
            return this;
        }

        public Builder changesFogDensity() {
            this.changeFogDensity = true;
            return this;
        }

        public Builder rendersSunSkyTint() {
            this.renderSunSkyTint = true;
            return this;
        }

        public Builder renderInThickFog(boolean renderInThickFog) {
            this.renderInTickFog = renderInThickFog;
            return this;
        }

        public Builder fogColors(RGBA fogColors) {
            this.fogColors = fogColors;
            return this;
        }

        public Builder rotation(Rotation rotation) {
            this.rotation = rotation;
            return this;
        }

        public Builder changeFog(boolean changeFog) {
            if (changeFog) {
                return this.changesFog();
            } else {
                return this;
            }
        }

        public Builder renderSunSkyTint(boolean renderSunSkyTint) {
            if (renderSunSkyTint) {
                return this.rendersSunSkyTint();
            } else {
                return this;
            }
        }

        public Properties build() {
            return new Properties(this.priority, this.fade, this.transitionInDuration, this.transitionOutDuration, this.changeFog, this.changeFogDensity, this.fogColors, this.renderSunSkyTint, this.renderInTickFog, this.rotation);
        }
    }
}
