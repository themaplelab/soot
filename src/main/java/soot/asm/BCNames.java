package soot.asm;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Kristen Newbury
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */


//a mapping, not relying on ddr, since few discreps                                                                 
//from https://github.com/eclipse/openj9/blob/v0.14.0-release/runtime/compiler/ilgen/J9ByteCodeIterator.cpp
public class BCNames{

    public static final int JBnop = 0;
    public static final int JBaconstnull = 1;
    public static final int JBiconstm1 = 2;
    public static final int JBiconst0 = 3;
    public static final int JBiconst1 = 4;
    public static final int JBiconst2 = 5;
    public static final int JBiconst3 = 6;
    public static final int JBiconst4 = 7;
    public static final int JBiconst5 = 8;
    public static final int JBlconst0 = 9;
    public static final int JBlconst1 = 10;
    public static final int JBfconst0 = 11;
    public static final int JBfconst1 = 12;
    public static final int JBfconst2 = 13;
    public static final int JBdconst0 = 14;
    public static final int JBdconst1 = 15;
    public static final int JBbipush = 16;
    public static final int JBsipush = 17;
    public static final int JBldc = 18;
    public static final int JBldcw = 19;
    public static final int JBldc2lw = 20;
    public static final int JBiload = 21;
    public static final int JBlload = 22;
    public static final int JBfload = 23;
    public static final int JBdload = 24;
    public static final int JBaload = 25;
    public static final int JBiload0 = 26;
    public static final int JBiload1 = 27;
    public static final int JBiload2 = 28;
    public static final int JBiload3 = 29;
    public static final int JBlload0 = 30;
    public static final int JBlload1 = 31;
    public static final int JBlload2 = 32;
    public static final int JBlload3 = 33;
    public static final int JBfload0 = 34;
    public static final int JBfload1 = 35;
    public static final int JBfload2 = 36;
    public static final int JBfload3 = 37;
    public static final int JBdload0 = 38;
    public static final int JBdload1 = 39;
    public static final int JBdload2 = 40;
    public static final int JBdload3 = 41;
    public static final int JBaload0 = 42;
    public static final int JBaload1 = 43;
    public static final int JBaload2 = 44;
    public static final int JBaload3 = 45;
    public static final int JBiaload = 46;
    public static final int JBlaload = 47;
    public static final int JBfaload = 48;
    public static final int JBdaload = 49;
    public static final int JBaaload = 50;
    public static final int JBbaload = 51;
    public static final int JBcaload = 52;
    public static final int JBsaload = 53;
    public static final int JBistore = 54;
    public static final int JBlstore = 55;
    public static final int JBfstore = 56;
    public static final int JBdstore = 57;
    public static final int JBastore = 58;
    public static final int JBistore0 = 59;
    public static final int JBistore1 = 60;
    public static final int JBistore2 = 61;
    public static final int JBistore3 = 62;
    public static final int JBlstore0 = 63;
    public static final int JBlstore1 = 64;
    public static final int JBlstore2 = 65;
    public static final int JBlstore3 = 66;
    public static final int JBfstore0 = 67;
    public static final int JBfstore1 = 68;
    public static final int JBfstore2 = 69;
    public static final int JBfstore3 = 70;
    public static final int JBdstore0 = 71;
    public static final int JBdstore1 = 72;
    public static final int JBdstore2 = 73;
    public static final int JBdstore3 = 74;
    public static final int JBastore0 = 75;
    public static final int JBastore1 = 76;
    public static final int JBastore2 = 77;
    public static final int JBastore3 = 78;
    public static final int JBiastore = 79;
    public static final int JBlastore = 80;
    public static final int JBfastore = 81;
    public static final int JBdastore = 82;
    public static final int JBaastore = 83;
    public static final int JBbastore = 84;
    public static final int JBcastore = 85;
    public static final int JBsastore = 86;
    public static final int JBpop = 87;
    public static final int JBpop2 = 88;
    public static final int JBdup = 89;
    public static final int JBdupx1 = 90;
    public static final int JBdupx2 = 91;
    public static final int JBdup2 = 92;
    public static final int JBdup2x1 = 93;
    public static final int JBdup2x2 = 94;
    public static final int JBswap= 95;
    public static final int JBiadd= 96;
    public static final int JBladd= 97;
    public static final int JBfadd= 98;
    public static final int JBdadd= 99;
    public static final int JBisub= 100;
    public static final int JBlsub= 101;
    public static final int JBfsub= 102;
    public static final int JBdsub= 103;
    public static final int JBimul= 104;
    public static final int JBlmul= 105;
    public static final int JBfmul= 106;
    public static final int JBdmul= 107;
    public static final int JBidiv= 108;
    public static final int JBldiv= 109;
    public static final int JBfdiv= 110;
    public static final int JBddiv= 111;
    public static final int JBirem= 112;
    public static final int JBlrem= 113;
    public static final int JBfrem= 114;
    public static final int JBdrem= 115;
    public static final int JBineg= 116;
    public static final int JBlneg= 117;
    public static final int JBfneg= 118;
    public static final int JBdneg= 119;
    public static final int JBishl= 120;
    public static final int JBlshl= 121;
    public static final int JBishr= 122;
    public static final int JBlshr= 123;
    public static final int JBiushr= 124;
    public static final int JBlushr= 125;
    public static final int JBiand= 126;
    public static final int JBland= 127;
    public static final int JBior= 128;
    public static final int JBlor= 129;
    public static final int JBixor= 130;
    public static final int JBlxor= 131;
    public static final int JBiinc= 132;
    public static final int JBi2l= 133;
    public static final int JBi2f= 134;
    public static final int JBi2d= 135;
    public static final int JBl2i= 136;
    public static final int JBl2f= 137;
    public static final int JBl2d= 138;
    public static final int JBf2i= 139;
    public static final int JBf2l= 140;
    public static final int JBf2d= 141;
    public static final int JBd2i= 142;
    public static final int JBd2l= 143;
    public static final int JBd2f= 144;
    public static final int JBi2b= 145;
    public static final int JBi2c= 146;
    public static final int JBi2s= 147;
    public static final int JBlcmp= 148;
    public static final int JBfcmpl= 149;
    public static final int JBfcmpg= 150;
    public static final int JBdcmpl= 151;
    public static final int JBdcmpg= 152;
    public static final int JBifeq= 153;
    public static final int JBifne= 154;
    public static final int JBiflt= 155;
    public static final int JBifge= 156;
    public static final int JBifgt= 157;
    public static final int JBifle= 158;
    public static final int JBificmpeq= 159;
    public static final int JBificmpne= 160;
    public static final int JBificmplt= 161;
    public static final int JBificmpge= 162;
    public static final int JBificmpgt= 163;
    public static final int JBificmple= 164;
    public static final int JBifacmpeq= 165;
    public static final int JBifacmpne= 166;
    public static final int JBgoto= 167;
    public static final int JBunknown= 168;
    public static final int JBunknown= 169;
    public static final int JBtableswitch= 170;
    public static final int JBlookupswitch= 171;
    public static final int JBgenericReturn= 172;
    public static final int JBgenericReturn= 173;
    public static final int JBgenericReturn= 174;
    public static final int JBgenericReturn= 175;
    public static final int JBgenericReturn= 176;
    public static final int JBgenericReturn= 177;
    public static final int JBgetstatic= 178;
    public static final int JBputstatic= 179;
    public static final int JBgetfield= 180;
    public static final int JBputfield= 181;
    public static final int JBinvokevirtual= 182;
    public static final int JBinvokespecial= 183;
    public static final int JBinvokestatic= 184;
    public static final int JBinvokeinterface= 185;
    public static final int JBinvokedynamic= 186;
    public static final int JBnew= 187;
    public static final int JBnewarray= 188;
    public static final int JBanewarray= 189;
    public static final int JBarraylength= 190;
    public static final int JBathrow= 191;
    public static final int JBcheckcast= 192;
    public static final int JBinstanceof= 193;
    public static final int JBmonitorenter= 194;
    public static final int JBmonitorexit= 195;
    public static final int JBunknown= 196;
    public static final int JBmultianewarray= 197;
    public static final int JBifnull= 198;
    public static final int JBifnonnull= 199;
    public static final int JBgotow= 200;
    public static final int JBunknown= 201;
    public static final int JBbreakpoint= 202;

    //if defined(JVM_OPT_VALHALLA_VALUE_TYPES)
    public static final int JBdefaultvalue= 203;
    public static final int JBwithfield= 204;
    //else /* defined(JVM_OPT_VALHALLA_VALUE_TYPES) */
    /* 203 */ //JBunknown= ;
    /* 204 */ //JBunknown= ;
    //endif
    
    public static final int JBunknown= 205;
    public static final int JBunknown= 206;
    public static final int JBunknown= 207;
    public static final int JBunknown= 208;
    public static final int JBunknown= 209;
    public static final int JBunknown= 210;
    public static final int JBunknown= 211;
    public static final int JBunknown= 212;
    public static final int JBiincw= 213;
    public static final int JBunknown= 214;
    public static final int JBaload0= 215;
    public static final int JBnew= 216;
    public static final int JBiloadw= 217;
    public static final int JBlloadw= 218;
    public static final int JBfloadw= 219;
    public static final int JBdloadw= 220;
    public static final int JBaloadw= 221;
    public static final int JBistorew= 222;
    public static final int JBlstorew= 223;
    public static final int JBfstorew= 224;
    public static final int JBdstorew= 225;
    public static final int JBastorew= 226;
    public static final int JBunknown= 227;
    public static final int JBgenericReturn= 228;
    public static final int JBgenericReturn= 229;
    public static final int JBunknown= 230;
    public static final int JBinvokeinterface2= 231;
    public static final int JBinvokehandle= 232;
    public static final int JBinvokehandlegeneric= 233;
    public static final int JBinvokestaticsplit= 234;
    public static final int JBinvokespecialsplit= 235;
    public static final int JBReturnC= 236;
    public static final int JBReturnS= 237;
    public static final int JBReturnB= 238;
    public static final int JBReturnZ= 239;
    public static final int JBunknown= 240;
    public static final int JBunknown= 241;
    public static final int JBunknown= 242;
    public static final int JBunknown= 243;
    public static final int JBunknown= 244;
    public static final int JBunknown= 245;
    public static final int JBunknown= 246;
    public static final int JBunknown= 247;
    public static final int JBunknown= 248;
    public static final int JBldc2dw= 249;
    public static final int JBunknown= 250;
    public static final int JBunknown= 251;
    public static final int JBunknown= 252;
    public static final int JBunknown= 253;
    public static final int JBunknown= 254;
    public static final int JBunknown=255;
   }

}
