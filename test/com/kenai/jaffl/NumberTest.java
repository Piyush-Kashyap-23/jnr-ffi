
package com.kenai.jaffl;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wayne
 */
public class NumberTest {

    public NumberTest() {
    }

    public static interface TestLib {

        public byte add_int8_t(byte i1, byte i2);

        public short add_int16_t(short i1, short i2);

        public int add_int32_t(int i1, int i2);
        public long add_int64_t(long i1, long i2);
        public NativeLong add_long(NativeLong i1, NativeLong i2);
        public NativeLong sub_long(NativeLong i1, NativeLong i2);
        public NativeLong mul_long(NativeLong i1, NativeLong i2);
        public NativeLong div_long(NativeLong i1, NativeLong i2);
        public float add_float(float f1, float f2);
        public float sub_float(float f1, float f2);
        public float mul_float(float f1, float f2);
        public float div_float(float f1, float f2);
        public double add_double(double f1, double f2);
        public double sub_double(double f1, double f2);
        public double mul_double(double f1, double f2);
        public double div_double(double f1, double f2);
    }
    static TestLib testlib;

    @BeforeClass
    public static void setUpClass() throws Exception {
        testlib = TstUtil.loadTestLib(TestLib.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
        
    @Test
    public void testByteAddition() throws Exception {
        for (int i = 0; i <= 255; ++i) {
            byte i1 = (byte) i;
            byte i2 = (byte) 0xde;
            assertEquals("byte addition failed", (byte) (i1 + i2), testlib.add_int8_t(i1, i2));
        }
    }
    @Test
    public void testShortAddition() throws Exception {
        for (int i = 0; i <= 0xffff; ++i) {
            short i1 = (short) i;
            short i2 = (short) 0xdead;
            assertEquals("byte addition failed", (short) (i1 + i2), testlib.add_int16_t(i1, i2));
        }
    }
    static interface FloatOp {
        public float j(float f1, float f2);
        public float n(float f1, float f2);
    }
    private void testFloat(FloatOp op) throws Exception {
        float f1 = 1.0f;
        float f2 = (float) 0xdeadbeef;
        assertEquals("float " + op + " failed", op.j(f1, f2), op.n(f1, f2), 0f);
        for (int i = 0; i < 0xffff; ++i) {
            f1 = (float) i;
            assertEquals("float + " + op + " failed", op.j(f1, f2), op.n(f1, f2), 0f);
        }
        Random random = new Random();
        for (int i = 0; i < 0xffff; ++i) {
            f1 = random.nextFloat();
            f2 = random.nextFloat();
            float expected = op.j(f1, f2);
            float result = op.n(f1, f2);
            if (expected != result) {
                fail(String.format("float " + op + "(%f, %f) failed - expected: %f, received: %f",
                        f1, f2, expected, result));
            }
        }  
    }
    @Test
    public void testFloatAddition() throws Exception {
        testFloat(new FloatOp() {

            public float j(float f1, float f2) {
                return f1 + f2;
            }

            public float n(float f1, float f2) {
                return testlib.add_float(f1, f2);
            }
            @Override
            public String toString() { return "add"; }
        });        
    }
    @Test
    public void testFloatSubtraction() throws Exception {
        testFloat(new FloatOp() {

            public float j(float f1, float f2) {
                return f1 - f2;
            }

            public float n(float f1, float f2) {
                return testlib.sub_float(f1, f2);
            }
            @Override
            public String toString() { return "subtract"; }
        });        
    }
    @Test
    public void testFloatMultiplication() throws Exception {
        testFloat(new FloatOp() {

            public float j(float f1, float f2) {
                return f1 * f2;
            }

            public float n(float f1, float f2) {
                return testlib.mul_float(f1, f2);
            }
            public String toString() { return "multiply"; }
        }); 
        
    }
    @Test
    public void testFloatDivision() throws Exception {
        testFloat(new FloatOp() {

            public float j(float f1, float f2) {
                return f1 / f2;
            }

            public float n(float f1, float f2) {
                return testlib.div_float(f1, f2);
            }
            @Override
            public String toString() { return "divide"; }
        }); 
        
    }
    static interface DoubleOp {
        public double j(double f1, double f2);
        public double n(double f1, double f2);
    }
    private void testDouble(DoubleOp op) throws Exception {
        double f1 = 1.0f;
        double f2 = (double) 0xdeadbeef;
        assertEquals("double " + op + " failed", op.j(f1, f2), op.n(f1, f2), 0d);
        for (int i = 0; i < 0xffff; ++i) {
            f1 = (float) i;
            assertEquals("double + " + op + " failed", op.j(f1, f2), op.n(f1, f2), 0d);
        }
        Random random = new Random();
        for (int i = 0; i < 0xffff; ++i) {
            f1 = random.nextFloat();
            f2 = random.nextFloat();
            double expected = op.j(f1, f2);
            double result = op.n(f1, f2);
            if (expected != result) {
                fail(String.format("double " + op + "(%f, %f) failed - expected: %f, received: %f",
                        f1, f2, expected, result));
            }
        }  
    }
    @Test
    public void testDoubleAddition() throws Exception {
        testDouble(new DoubleOp() {

            public double j(double f1, double f2) {
                return f1 + f2;
            }

            public double n(double f1, double f2) {
                return testlib.add_double(f1, f2);
            }
            @Override
            public String toString() { return "add"; }
        });        
    }
    @Test
    public void testDoubleSubtraction() throws Exception {
        testDouble(new DoubleOp() {

            public double j(double f1, double f2) {
                return f1 - f2;
            }

            public double n(double f1, double f2) {
                return testlib.sub_double(f1, f2);
            }
            @Override
            public String toString() { return "subtract"; }
        });        
    }
    @Test
    public void testDoubleMultiplication() throws Exception {
        testDouble(new DoubleOp() {

            public double j(double f1, double f2) {
                return f1 * f2;
            }

            public double n(double f1, double f2) {
                return testlib.mul_double(f1, f2);
            }
            @Override
            public String toString() { return "multiply"; }
        }); 
        
    }
    @Test
    public void testDoubleDivision() throws Exception {
        testDouble(new DoubleOp() {

            public double j(double f1, double f2) {
                return f1 / f2;
            }

            public double n(double f1, double f2) {
                return testlib.div_double(f1, f2);
            }
            @Override
            public String toString() { return "divide"; }
        }); 
        
    }
    static interface NativeLongOp {
        public long j(long f1, long f2);
        public long n(long f1, long f2);
    }
    private void testNativeLong(NativeLongOp op) throws Exception {
        long i1 = 1;
        long i2 = 2;
        assertEquals("NativeLong " + op + " failed", op.j(i1, i2), op.n(i1, i2));
        for (int i = 0; i < 0xffff; ++i) {
            assertEquals("NativeLong + " + op + " failed", op.j(i, i2), op.n(i, i2));
        }
    }
    @Test
    public void NativeLong_valueOf() {
        for (int i = -1000; i < 1000; ++i) {
            assertEquals("Incorrect value from valueOf(" + i+ ")", i, NativeLong.valueOf(i).intValue());
        }
        for (long i = -1000; i < 1000; ++i) {
            assertEquals("Incorrect value from valueOf(" + i+ ")", i, NativeLong.valueOf(i).longValue());
        }
    }
    
    @Test
    public void testNativeLongAddition() throws Exception {
        testNativeLong(new NativeLongOp() {

            public long j(long i1, long i2) {
                return i1 + i2;
            }

            public long n(long i1, long i2) {
                return testlib.add_long(NativeLong.valueOf(i1), NativeLong.valueOf(i2)).longValue();
            }
        });
    }
}