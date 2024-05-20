package com.vincentcodes.math;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvolutionOptions {
    /**
     * true if flipping the matrix by 180deg is needed.
     */
    @Builder.Default
    public boolean flip = false;

    /**
     * pad with 0 ([0] -> padSize = 1 -> [[0, 0, 0], [0, 0, 0], [0, 0, 0]])
     */
    @Builder.Default
    public int padSize = 0;

    /**
     * stride / jump length
     */
    @Builder.Default
    public int strideSize = 1;
}
