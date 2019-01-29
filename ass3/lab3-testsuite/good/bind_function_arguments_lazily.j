.class public bind_function_arguments_lazily

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



  invokestatic bind_function_arguments_lazily/main()I

  pop

  return



.end method



.method public static snd(II)I
.limit locals 1000
.limit stack 1000
iload_1
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


iconst_1
iload_0
invokestatic bind_function_arguments_lazily/snd(II)I
istore_1


iload_1
invokestatic Runtime/printInt(I)V


iload_1
ireturn


return
.end method
