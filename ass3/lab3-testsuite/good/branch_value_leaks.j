.class public branch_value_leaks

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



  invokestatic branch_value_leaks/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


iconst_0
ifeq L1
iconst_0
pop


goto L0
L1:
iconst_1
istore_1


L0:


iload_0
dup
iconst_1
isub
istore_0
istore_1


iconst_0
ireturn


return
.end method
