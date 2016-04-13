package io.qala.datagen;

import static io.qala.datagen.RandomShortApi.alphanumeric;

/**
 * Not finished yet.
 */
class RandomUri {

    private final Long min;
    private final Long max;

    RandomUri(Long min, Long max) {
        this.min = min;
        this.max = max;
    }

    public static String url() {
        return "http://" + alphanumeric(1, 10) + "." + alphanumeric(1, 10) +
                "/" + alphanumeric(1, 10) + "/" + alphanumeric(1, 10) +
                "?" + alphanumeric(1, 10) + "=" + alphanumeric(1, 10) +
                "&" + alphanumeric(1, 10) + "=" + alphanumeric(1, 10) +
                "#" + alphanumeric(1, 10);
    }
}
