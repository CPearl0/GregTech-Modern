package com.gregtechceu.gtceu.api.block;

import com.gregtechceu.gtceu.client.model.IGTCTMPredicate;
import com.lowdragmc.lowdraglib.client.renderer.IBlockRendererProvider;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActiveBlock extends Block implements IBlockRendererProvider, IGTCTMPredicate {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    private final IRenderer renderer;
    private final IRenderer activeRenderer;

    public ActiveBlock(Properties properties, IRenderer renderer, IRenderer activeRenderer) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(ACTIVE, false));
        this.renderer = renderer;
        this.activeRenderer = activeRenderer;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE);
    }

    public BlockState changeActive(BlockState state, boolean active) {
        if (state.is(this)) {
            return state.setValue(ACTIVE, active);
        }
        return state;
    }

    public boolean isActive(BlockState state) {
        return state.getValue(ACTIVE);
    }

    @Nullable
    @Override
    public IRenderer getRenderer(BlockState state) {
        return isActive(state) ? activeRenderer : renderer;
    }

}