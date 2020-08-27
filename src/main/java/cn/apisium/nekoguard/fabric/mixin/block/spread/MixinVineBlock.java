package cn.apisium.nekoguard.fabric.mixin.block.spread;

import net.minecraft.block.Block;
import net.minecraft.block.VineBlock;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(VineBlock.class)
public abstract class MixinVineBlock extends Block {

    public MixinVineBlock(Settings settings) {
        super(settings);
    }
    // BlockVine 184
    // BlockVine 186
    // BlockVine 191
    // BlockVine 193
    // BlockVine 195
    // BlockVine 227
    // BlockVine 242
    // WIP
}
