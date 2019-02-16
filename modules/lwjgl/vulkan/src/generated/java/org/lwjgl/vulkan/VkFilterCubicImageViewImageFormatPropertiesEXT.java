/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.vulkan;

import javax.annotation.*;

import java.nio.*;

import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;

/**
 * Structure for querying cubic filtering capabilities of an image view type.
 * 
 * <h5>Valid Usage (Implicit)</h5>
 * 
 * <ul>
 * <li>{@code sType} <b>must</b> be {@link EXTFilterCubic#VK_STRUCTURE_TYPE_FILTER_CUBIC_IMAGE_VIEW_IMAGE_FORMAT_PROPERTIES_EXT STRUCTURE_TYPE_FILTER_CUBIC_IMAGE_VIEW_IMAGE_FORMAT_PROPERTIES_EXT}</li>
 * </ul>
 * 
 * <h5>Valid Usage</h5>
 * 
 * <ul>
 * <li>If the {@code pNext} chain of the {@link VkImageFormatProperties2} structure contains an instance of {@link VkFilterCubicImageViewImageFormatPropertiesEXT}, the {@code pNext} chain of the {@link VkPhysicalDeviceImageFormatInfo2} structure <b>must</b> contain an instance of {@link VkPhysicalDeviceImageViewImageFormatInfoEXT} with an {@code imageViewType} that is compatible with {@code imageType}.</li>
 * </ul>
 * 
 * <h3>Member documentation</h3>
 * 
 * <ul>
 * <li>{@code sType} &ndash; the type of this structure.</li>
 * <li>{@code pNext} &ndash; {@code NULL} or a pointer to an extension-specific structure.</li>
 * <li>{@code filterCubic} &ndash; tells if image format, image type and image view type <b>can</b> be used with cubic filtering. This field is set by the implementation. User-specified value is ignored.</li>
 * <li>{@code filterCubicMinmax} &ndash; tells if image format, image type and image view type <b>can</b> be used with cubic filtering and minmax filtering. This field is set by the implementation. User-specified value is ignored.</li>
 * </ul>
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct VkFilterCubicImageViewImageFormatPropertiesEXT {
 *     VkStructureType sType;
 *     void * pNext;
 *     VkBool32 filterCubic;
 *     VkBool32 filterCubicMinmax;
 * }</code></pre>
 */
public class VkFilterCubicImageViewImageFormatPropertiesEXT extends Struct {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        STYPE,
        PNEXT,
        FILTERCUBIC,
        FILTERCUBICMINMAX;

    static {
        Layout layout = __struct(
            __member(4),
            __member(POINTER_SIZE),
            __member(4),
            __member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        STYPE = layout.offsetof(0);
        PNEXT = layout.offsetof(1);
        FILTERCUBIC = layout.offsetof(2);
        FILTERCUBICMINMAX = layout.offsetof(3);
    }

    /**
     * Creates a {@code VkFilterCubicImageViewImageFormatPropertiesEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public VkFilterCubicImageViewImageFormatPropertiesEXT(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** Returns the value of the {@code sType} field. */
    @NativeType("VkStructureType")
    public int sType() { return nsType(address()); }
    /** Returns the value of the {@code pNext} field. */
    @NativeType("void *")
    public long pNext() { return npNext(address()); }
    /** Returns the value of the {@code filterCubic} field. */
    @NativeType("VkBool32")
    public boolean filterCubic() { return nfilterCubic(address()) != 0; }
    /** Returns the value of the {@code filterCubicMinmax} field. */
    @NativeType("VkBool32")
    public boolean filterCubicMinmax() { return nfilterCubicMinmax(address()) != 0; }

    /** Sets the specified value to the {@code sType} field. */
    public VkFilterCubicImageViewImageFormatPropertiesEXT sType(@NativeType("VkStructureType") int value) { nsType(address(), value); return this; }
    /** Sets the specified value to the {@code pNext} field. */
    public VkFilterCubicImageViewImageFormatPropertiesEXT pNext(@NativeType("void *") long value) { npNext(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    public VkFilterCubicImageViewImageFormatPropertiesEXT set(
        int sType,
        long pNext
    ) {
        sType(sType);
        pNext(pNext);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public VkFilterCubicImageViewImageFormatPropertiesEXT set(VkFilterCubicImageViewImageFormatPropertiesEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code VkFilterCubicImageViewImageFormatPropertiesEXT} instance for the specified memory address. */
    public static VkFilterCubicImageViewImageFormatPropertiesEXT create(long address) {
        return wrap(VkFilterCubicImageViewImageFormatPropertiesEXT.class, address);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    @Nullable
    public static VkFilterCubicImageViewImageFormatPropertiesEXT createSafe(long address) {
        return address == NULL ? null : wrap(VkFilterCubicImageViewImageFormatPropertiesEXT.class, address);
    }

    /**
     * Create a {@link VkFilterCubicImageViewImageFormatPropertiesEXT.Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static VkFilterCubicImageViewImageFormatPropertiesEXT.Buffer create(long address, int capacity) {
        return wrap(Buffer.class, address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    @Nullable
    public static VkFilterCubicImageViewImageFormatPropertiesEXT.Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : wrap(Buffer.class, address, capacity);
    }

    // -----------------------------------

    /** Unsafe version of {@link #sType}. */
    public static int nsType(long struct) { return UNSAFE.getInt(null, struct + VkFilterCubicImageViewImageFormatPropertiesEXT.STYPE); }
    /** Unsafe version of {@link #pNext}. */
    public static long npNext(long struct) { return memGetAddress(struct + VkFilterCubicImageViewImageFormatPropertiesEXT.PNEXT); }
    /** Unsafe version of {@link #filterCubic}. */
    public static int nfilterCubic(long struct) { return UNSAFE.getInt(null, struct + VkFilterCubicImageViewImageFormatPropertiesEXT.FILTERCUBIC); }
    /** Unsafe version of {@link #filterCubicMinmax}. */
    public static int nfilterCubicMinmax(long struct) { return UNSAFE.getInt(null, struct + VkFilterCubicImageViewImageFormatPropertiesEXT.FILTERCUBICMINMAX); }

    /** Unsafe version of {@link #sType(int) sType}. */
    public static void nsType(long struct, int value) { UNSAFE.putInt(null, struct + VkFilterCubicImageViewImageFormatPropertiesEXT.STYPE, value); }
    /** Unsafe version of {@link #pNext(long) pNext}. */
    public static void npNext(long struct, long value) { memPutAddress(struct + VkFilterCubicImageViewImageFormatPropertiesEXT.PNEXT, value); }

    // -----------------------------------

    /** An array of {@link VkFilterCubicImageViewImageFormatPropertiesEXT} structs. */
    public static class Buffer extends StructBuffer<VkFilterCubicImageViewImageFormatPropertiesEXT, Buffer> {

        private static final VkFilterCubicImageViewImageFormatPropertiesEXT ELEMENT_FACTORY = VkFilterCubicImageViewImageFormatPropertiesEXT.create(-1L);

        /**
         * Creates a new {@code VkFilterCubicImageViewImageFormatPropertiesEXT.Buffer} instance backed by the specified container.
         *
         * Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link VkFilterCubicImageViewImageFormatPropertiesEXT#SIZEOF}, and its mark will be undefined.
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(container, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected VkFilterCubicImageViewImageFormatPropertiesEXT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code sType} field. */
        @NativeType("VkStructureType")
        public int sType() { return VkFilterCubicImageViewImageFormatPropertiesEXT.nsType(address()); }
        /** Returns the value of the {@code pNext} field. */
        @NativeType("void *")
        public long pNext() { return VkFilterCubicImageViewImageFormatPropertiesEXT.npNext(address()); }
        /** Returns the value of the {@code filterCubic} field. */
        @NativeType("VkBool32")
        public boolean filterCubic() { return VkFilterCubicImageViewImageFormatPropertiesEXT.nfilterCubic(address()) != 0; }
        /** Returns the value of the {@code filterCubicMinmax} field. */
        @NativeType("VkBool32")
        public boolean filterCubicMinmax() { return VkFilterCubicImageViewImageFormatPropertiesEXT.nfilterCubicMinmax(address()) != 0; }

        /** Sets the specified value to the {@code sType} field. */
        public VkFilterCubicImageViewImageFormatPropertiesEXT.Buffer sType(@NativeType("VkStructureType") int value) { VkFilterCubicImageViewImageFormatPropertiesEXT.nsType(address(), value); return this; }
        /** Sets the specified value to the {@code pNext} field. */
        public VkFilterCubicImageViewImageFormatPropertiesEXT.Buffer pNext(@NativeType("void *") long value) { VkFilterCubicImageViewImageFormatPropertiesEXT.npNext(address(), value); return this; }

    }

}