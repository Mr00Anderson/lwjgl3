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
 * Structure describing discard rectangle limits that can be supported by an implementation.
 * 
 * <h5>Description</h5>
 * 
 * <p>If the {@link VkPhysicalDeviceDiscardRectanglePropertiesEXT} structure is included in the {@code pNext} chain of {@link VkPhysicalDeviceProperties2}, it is filled with the implementation-dependent limits.</p>
 * 
 * <h5>Valid Usage (Implicit)</h5>
 * 
 * <ul>
 * <li>{@code sType} <b>must</b> be {@link EXTDiscardRectangles#VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_DISCARD_RECTANGLE_PROPERTIES_EXT STRUCTURE_TYPE_PHYSICAL_DEVICE_DISCARD_RECTANGLE_PROPERTIES_EXT}</li>
 * </ul>
 * 
 * <h3>Member documentation</h3>
 * 
 * <ul>
 * <li>{@code sType} &ndash; the type of this structure.</li>
 * <li>{@code pNext} &ndash; {@code NULL} or a pointer to an extension-specific structure.</li>
 * <li>{@code maxDiscardRectangles} &ndash; the maximum number of active discard rectangles that <b>can</b> be specified.</li>
 * </ul>
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>
 * struct VkPhysicalDeviceDiscardRectanglePropertiesEXT {
 *     VkStructureType sType;
 *     void * pNext;
 *     uint32_t maxDiscardRectangles;
 * }</code></pre>
 */
public class VkPhysicalDeviceDiscardRectanglePropertiesEXT extends Struct {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        STYPE,
        PNEXT,
        MAXDISCARDRECTANGLES;

    static {
        Layout layout = __struct(
            __member(4),
            __member(POINTER_SIZE),
            __member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        STYPE = layout.offsetof(0);
        PNEXT = layout.offsetof(1);
        MAXDISCARDRECTANGLES = layout.offsetof(2);
    }

    /**
     * Creates a {@code VkPhysicalDeviceDiscardRectanglePropertiesEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public VkPhysicalDeviceDiscardRectanglePropertiesEXT(ByteBuffer container) {
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
    /** Returns the value of the {@code maxDiscardRectangles} field. */
    @NativeType("uint32_t")
    public int maxDiscardRectangles() { return nmaxDiscardRectangles(address()); }

    /** Sets the specified value to the {@code sType} field. */
    public VkPhysicalDeviceDiscardRectanglePropertiesEXT sType(@NativeType("VkStructureType") int value) { nsType(address(), value); return this; }
    /** Sets the specified value to the {@code pNext} field. */
    public VkPhysicalDeviceDiscardRectanglePropertiesEXT pNext(@NativeType("void *") long value) { npNext(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    public VkPhysicalDeviceDiscardRectanglePropertiesEXT set(
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
    public VkPhysicalDeviceDiscardRectanglePropertiesEXT set(VkPhysicalDeviceDiscardRectanglePropertiesEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code VkPhysicalDeviceDiscardRectanglePropertiesEXT} instance for the specified memory address. */
    public static VkPhysicalDeviceDiscardRectanglePropertiesEXT create(long address) {
        return wrap(VkPhysicalDeviceDiscardRectanglePropertiesEXT.class, address);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    @Nullable
    public static VkPhysicalDeviceDiscardRectanglePropertiesEXT createSafe(long address) {
        return address == NULL ? null : wrap(VkPhysicalDeviceDiscardRectanglePropertiesEXT.class, address);
    }

    /**
     * Create a {@link VkPhysicalDeviceDiscardRectanglePropertiesEXT.Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static VkPhysicalDeviceDiscardRectanglePropertiesEXT.Buffer create(long address, int capacity) {
        return wrap(Buffer.class, address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    @Nullable
    public static VkPhysicalDeviceDiscardRectanglePropertiesEXT.Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : wrap(Buffer.class, address, capacity);
    }

    // -----------------------------------

    /** Unsafe version of {@link #sType}. */
    public static int nsType(long struct) { return UNSAFE.getInt(null, struct + VkPhysicalDeviceDiscardRectanglePropertiesEXT.STYPE); }
    /** Unsafe version of {@link #pNext}. */
    public static long npNext(long struct) { return memGetAddress(struct + VkPhysicalDeviceDiscardRectanglePropertiesEXT.PNEXT); }
    /** Unsafe version of {@link #maxDiscardRectangles}. */
    public static int nmaxDiscardRectangles(long struct) { return UNSAFE.getInt(null, struct + VkPhysicalDeviceDiscardRectanglePropertiesEXT.MAXDISCARDRECTANGLES); }

    /** Unsafe version of {@link #sType(int) sType}. */
    public static void nsType(long struct, int value) { UNSAFE.putInt(null, struct + VkPhysicalDeviceDiscardRectanglePropertiesEXT.STYPE, value); }
    /** Unsafe version of {@link #pNext(long) pNext}. */
    public static void npNext(long struct, long value) { memPutAddress(struct + VkPhysicalDeviceDiscardRectanglePropertiesEXT.PNEXT, value); }

    // -----------------------------------

    /** An array of {@link VkPhysicalDeviceDiscardRectanglePropertiesEXT} structs. */
    public static class Buffer extends StructBuffer<VkPhysicalDeviceDiscardRectanglePropertiesEXT, Buffer> {

        private static final VkPhysicalDeviceDiscardRectanglePropertiesEXT ELEMENT_FACTORY = VkPhysicalDeviceDiscardRectanglePropertiesEXT.create(-1L);

        /**
         * Creates a new {@code VkPhysicalDeviceDiscardRectanglePropertiesEXT.Buffer} instance backed by the specified container.
         *
         * Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link VkPhysicalDeviceDiscardRectanglePropertiesEXT#SIZEOF}, and its mark will be undefined.
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
        protected VkPhysicalDeviceDiscardRectanglePropertiesEXT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Returns the value of the {@code sType} field. */
        @NativeType("VkStructureType")
        public int sType() { return VkPhysicalDeviceDiscardRectanglePropertiesEXT.nsType(address()); }
        /** Returns the value of the {@code pNext} field. */
        @NativeType("void *")
        public long pNext() { return VkPhysicalDeviceDiscardRectanglePropertiesEXT.npNext(address()); }
        /** Returns the value of the {@code maxDiscardRectangles} field. */
        @NativeType("uint32_t")
        public int maxDiscardRectangles() { return VkPhysicalDeviceDiscardRectanglePropertiesEXT.nmaxDiscardRectangles(address()); }

        /** Sets the specified value to the {@code sType} field. */
        public VkPhysicalDeviceDiscardRectanglePropertiesEXT.Buffer sType(@NativeType("VkStructureType") int value) { VkPhysicalDeviceDiscardRectanglePropertiesEXT.nsType(address(), value); return this; }
        /** Sets the specified value to the {@code pNext} field. */
        public VkPhysicalDeviceDiscardRectanglePropertiesEXT.Buffer pNext(@NativeType("void *") long value) { VkPhysicalDeviceDiscardRectanglePropertiesEXT.npNext(address(), value); return this; }

    }

}