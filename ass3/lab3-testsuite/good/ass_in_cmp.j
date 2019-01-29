.class public ass_in_cmp

.super java/lang/Object



.method public <init>()V

  .limit locals 1



  aload_0

  invokespecial java/lang/Object/<init>()V

  return



.end method



.method public static main([Ljava/lang/String;)V

  .limit locals 1

  .limit stack  1



  invokestatic ass_in_cmp/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_5
istore_0


iconst_1
iload_0
iconst_1
iadd
dup
istore_0
iload_0
dup
iconst_1
iadd
istore_0
if_icmpeq L2
pop
iconst_0
L2:
ifeq L1
iload_0
invokestatic Runtime/printInt(I)V


goto L0
L1:
iconst_0
invokestatic Runtime/printInt(I)V


L0:


iconst_0
ireturn


return
.end method
