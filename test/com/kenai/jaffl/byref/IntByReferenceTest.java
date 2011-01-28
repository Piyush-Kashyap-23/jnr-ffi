
package com.kenai.jaffl.byref;

import com.kenai.jaffl.TstUtil;
import com.kenai.jaffl.annotations.In;
import com.kenai.jaffl.annotations.Out;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class IntByReferenceTest {
    public IntByReferenceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    public static interface TestLib {
        int ptr_ret_int32_t(IntByReference p, int offset);
        void ptr_set_int32_t(IntByReference p, int offset, int value);
    }
    public static interface TestLibInOnly {
        int ptr_ret_int32_t(@In IntByReference p, int offset);
        void ptr_set_int32_t(@In IntByReference p, int offset, int value);
    }
    public static interface TestLibOutOnly {
        int ptr_ret_int32_t(@Out IntByReference p, int offset);
        void ptr_set_int32_t(@Out IntByReference p, int offset, int value);
    }
    
    @Test public void inOnlyIntReferenceSet() {
        TestLibInOnly lib = TstUtil.loadTestLib(TestLibInOnly.class);
        final int MAGIC = 0xdeadbeef;
        IntByReference ref = new IntByReference(MAGIC);
        assertEquals("Wrong value passed", MAGIC, lib.ptr_ret_int32_t(ref, 0));
    }
    @Test public void inOnlyIntReferenceNotWritten() {
        TestLibInOnly lib = TstUtil.loadTestLib(TestLibInOnly.class);
        final int MAGIC = 0xdeadbeef;
        IntByReference ref = new IntByReference(MAGIC);
        lib.ptr_set_int32_t(ref, 0, 0);
        assertEquals("Int reference written when it should not be", Integer.valueOf(MAGIC), ref.getValue());
    }
    @Test public void outOnlyIntReferenceNotRead() {
        TestLibOutOnly lib = TstUtil.loadTestLib(TestLibOutOnly.class);
        final int MAGIC = 0xdeadbeef;
        IntByReference ref = new IntByReference(MAGIC);
        assertTrue("Reference value passed to native code when it should not be", MAGIC != lib.ptr_ret_int32_t(ref, 0));
    }
    @Test public void outOnlyIntReferenceGet() {
        TestLibOutOnly lib = TstUtil.loadTestLib(TestLibOutOnly.class);
        final int MAGIC = 0xdeadbeef;
        IntByReference ref = new IntByReference(0);
        lib.ptr_set_int32_t(ref, 0, MAGIC);
        assertEquals("Reference value not set", Integer.valueOf(MAGIC), ref.getValue());
    }
}