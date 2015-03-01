package net.epoxide.colorfulmobs.common;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ColorProperties implements IExtendedEntityProperties {

    public static final String PROP_NAME = "ColorProperties";

    public EntityLivingBase entity;
    public ColorObject colorObj;

    public ColorProperties(EntityLivingBase living) {

        entity = living;
        colorObj = new ColorObject(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {

        compound.setTag(PROP_NAME, ColorObject.getTagFromColor(this.colorObj));
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {

        this.colorObj = ColorObject.getColorFromTag(compound.getCompoundTag(PROP_NAME));
        ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
    }

    @Override
    public void init(Entity entity, World world) {

    }

    /**
     * Retrieves a ColorProperties object from a provided entity.
     * 
     * @param living: A living entity which extends EntityLivingBase, this is where the data comes from.
     * @return ColorProperties: A ColorProperties object unique to the specified living entity.
     */
    public static ColorProperties getPropsFromEntity(EntityLivingBase living) {

        return (ColorProperties) living.getExtendedProperties(PROP_NAME);
    }

    /**
     * Sets a new ColorProperties object to a living entity, this will override any existing color data,
     * and is mandatory to apply before setting colors to mobs.
     * 
     * @param living
     */
    public static void setPropsToEntity(EntityLivingBase living) {

        living.registerExtendedProperties(PROP_NAME, new ColorProperties(living));
    }

    /**
     * Checks to see if a living entity has color properties.
     * 
     * @param living: A living entity to check for colored properties.
     * @return boolean: True if the mob has a ColorProperties object.
     */
    public static boolean hasColorProperties(EntityLivingBase living) {

        return getPropsFromEntity(living) != null;
    }

    /**
     * Sets the color for an entity. Has built in check to ensure the mob actually has a ColorProperties
     * object to write to.
     * 
     * @param color: A ColorObject containing the color being set. This will override existing colors.
     * @param living: A living entity to have color data applied to.
     */
    public static void setEntityColors(ColorObject color, EntityLivingBase living) {

        if (!hasColorProperties(living))
            setPropsToEntity(living);

        ColorProperties props = getPropsFromEntity(living);
        props.colorObj = color;
    }
}