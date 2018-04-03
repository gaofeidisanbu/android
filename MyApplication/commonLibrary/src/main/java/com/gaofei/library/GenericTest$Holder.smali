.class public Lcom/gaofei/library/GenericTest$Holder;
.super Ljava/lang/Object;
.source "GenericTest.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/gaofei/library/GenericTest;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Holder"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;"
    }
.end annotation


# instance fields
.field private t:Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "TT;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 23
    .local p0, "this":Lcom/gaofei/library/GenericTest$Holder;, "Lcom/gaofei/library/GenericTest$Holder<TT;>;"
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public getTarget()Ljava/lang/Object;
    .registers 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()TT;"
        }
    .end annotation

    .prologue
    .line 31
    .local p0, "this":Lcom/gaofei/library/GenericTest$Holder;, "Lcom/gaofei/library/GenericTest$Holder<TT;>;"
    iget-object v0, p0, Lcom/gaofei/library/GenericTest$Holder;->t:Ljava/lang/Object;

    return-object v0
.end method

.method public setTarget(Ljava/lang/Object;)V
    .registers 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;)V"
        }
    .end annotation

    .prologue
    .line 28
    .local p0, "this":Lcom/gaofei/library/GenericTest$Holder;, "Lcom/gaofei/library/GenericTest$Holder<TT;>;"
    .local p1, "t":Ljava/lang/Object;, "TT;"
    return-void
.end method
