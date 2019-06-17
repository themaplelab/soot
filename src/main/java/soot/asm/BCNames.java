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
    public static final int JBCswap= 95;
    public static final int JBCiadd= 96;
    public static final int JBCladd= 97;
    public static final int JBCfadd= 98;
    public static final int JBCdadd= 99;
    public static final int JBCisub= 100;
    public static final int JBClsub= 101;
    public static final int JBCfsub= 102;
    public static final int JBCdsub= 103;
    public static final int JBCimul= 104;
    public static final int JBClmul= 105;
    public static final int JBCfmul= 106;
    public static final int JBCdmul= 107;
    public static final int JBCidiv= 108;
    public static final int JBCldiv= 109;
    public static final int JBCfdiv= 110;
    public static final int JBCddiv= 111;
    public static final int JBCirem= 112;
    public static final int JBClrem= 113;
    public static final int JBCfrem= 114;
    public static final int JBCdrem= 115;
    public static final int JBCineg= 116;
    public static final int JBClneg= 117;
    public static final int JBCfneg= 118;
    public static final int JBCdneg= 119;
    public static final int JBCishl= 120;
    public static final int JBClshl= 121;
    public static final int JBCishr= 122;
    public static final int JBClshr= 123;
    public static final int JBCiushr= 124;
    public static final int JBClushr= 125;
    public static final int JBCiand= 126;
    public static final int JBCland= 127;
    public static final int JBCior= 128;
    public static final int JBClor= 129;
    public static final int JBCixor= 130;
    public static final int JBClxor= 131;
    public static final int JBCiinc= 132;
    public static final int JBCi2l= 133;
    public static final int JBCi2f= 134;
    public static final int JBCi2d= 135;
    public static final int JBCl2i= 136;
    public static final int JBCl2f= 137;
    public static final int JBCl2d= 138;
    public static final int JBCf2i= 139;
    public static final int JBCf2l= 140;
    public static final int JBCf2d= 141;
    public static final int JBCd2i= 142;
    public static final int JBCd2l= 143;
    public static final int JBCd2f= 144;
    public static final int JBCi2b= 145;
    public static final int JBCi2c= 146;
    public static final int JBCi2s= 147;
    public static final int JBClcmp= 148;
    public static final int JBCfcmpl= 149;
    public static final int JBCfcmpg= 150;
    public static final int JBCdcmpl= 151;
    public static final int JBCdcmpg= 152;
    public static final int JBCifeq= 153;
    public static final int JBCifne= 154;
    public static final int JBCiflt= 155;
    public static final int JBCifge= 156;
    public static final int JBCifgt= 157;
    public static final int JBCifle= 158;
    public static final int JBCificmpeq= 159;
    public static final int JBCificmpne= 160;
    public static final int JBCificmplt= 161;
    public static final int JBCificmpge= 162;
    public static final int JBCificmpgt= 163;
    public static final int JBCificmple= 164;
    public static final int JBCifacmpeq= 165;
    public static final int JBCifacmpne= 166;
    public static final int JBCgoto= 167;
    public static final int JBCunknown= 168;
    public static final int JBCunknown= 169;
    public static final int JBCtableswitch= 170;
    public static final int JBClookupswitch= 171;
    public static final int JBCgenericReturn= 172;
    public static final int JBCgenericReturn= 173;
    public static final int JBCgenericReturn= 174;
    public static final int JBCgenericReturn= 175;
    public static final int JBCgenericReturn= 176;
    public static final int JBCgenericReturn= 177;
    public static final int JBCgetstatic= 178;
    public static final int JBCputstatic= 179;
    public static final int JBCgetfield= 180;
    public static final int JBCputfield= 181;
    public static final int JBCinvokevirtual= 182;
    public static final int JBCinvokespecial= 183;
    public static final int JBCinvokestatic= 184;
    public static final int JBCinvokeinterface= 185;
    public static final int JBCinvokedynamic= 186;
    public static final int JBCnew= 187;
    public static final int JBCnewarray= 188;
    public static final int JBCanewarray= 189;
    public static final int JBCarraylength= 190;
    public static final int JBCathrow= 191;
    public static final int JBCcheckcast= 192;
    public static final int JBCinstanceof= 193;
    public static final int JBCmonitorenter= 194;
    public static final int JBCmonitorexit= 195;
    public static final int JBCunknown= 196;
    public static final int JBCmultianewarray= 197;
    public static final int JBCifnull= 198;
    public static final int JBCifnonnull= 199;
    public static final int JBCgotow= 200;
    public static final int JBCunknown= 201;
    public static final int JBCbreakpoint= 202;

    //if defined(JVM_OPT_VALHALLA_VALUE_TYPES)
    public static final int JBCdefaultvalue= 203;
    public static final int JBCwithfield= 204;
    //else /* defined(JVM_OPT_VALHALLA_VALUE_TYPES) */
    /* 203 */ //JBCunknown= ;
    /* 204 */ //JBCunknown= ;
    //endif
    
    public static final int JBCunknown= 205;
    public static final int JBCunknown= 206;
    public static final int JBCunknown= 207;
    public static final int JBCunknown= 208;
    public static final int JBCunknown= 209;
    public static final int JBCunknown= 210;
    public static final int JBCunknown= 211;
    public static final int JBCunknown= 212;
    public static final int JBCiincw= 213;
    public static final int JBCunknown= 214;
    public static final int JBCaload0= 215;
    public static final int JBCnew= 216;
    public static final int JBCiloadw= 217;
    public static final int JBClloadw= 218;
    public static final int JBCfloadw= 219;
    public static final int JBCdloadw= 220;
    public static final int JBCaloadw= 221;
    public static final int JBCistorew= 222;
    public static final int JBClstorew= 223;
    public static final int JBCfstorew= 224;
    public static final int JBCdstorew= 225;
    public static final int JBCastorew= 226;
    public static final int JBCunknown= 227;
    public static final int JBCgenericReturn= 228;
    public static final int JBCgenericReturn= 229;
    public static final int JBCunknown= 230;
    public static final int JBCinvokeinterface2= 231;
    public static final int JBCinvokehandle= 232;
    public static final int JBCinvokehandlegeneric= 233;
    public static final int JBCinvokestaticsplit= 234;
    public static final int JBCinvokespecialsplit= 235;
    public static final int JBCReturnC= 236;
    public static final int JBCReturnS= 237;
    public static final int JBCReturnB= 238;
    public static final int JBCReturnZ= 239;
    public static final int JBCunknown= 240;
    public static final int JBCunknown= 241;
    public static final int JBCunknown= 242;
    public static final int JBCunknown= 243;
    public static final int JBCunknown= 244;
    public static final int JBCunknown= 245;
    public static final int JBCunknown= 246;
    public static final int JBCunknown= 247;
    public static final int JBCunknown= 248;
    public static final int JBCldc2dw= 249;
    public static final int JBCunknown= 250;
    public static final int JBCunknown= 251;
    public static final int JBCunknown= 252;
    public static final int JBCunknown= 253;
    public static final int JBCunknown= 254;
    public static final int JBCunknown=255;
   }

}
