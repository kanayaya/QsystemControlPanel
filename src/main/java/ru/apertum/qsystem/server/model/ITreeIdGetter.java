package ru.apertum.qsystem.server.model;

import ru.apertum.qsystem.swing.MutableTreeNode;

public interface ITreeIdGetter extends IidGetter, MutableTreeNode {
   Long getParentId();

   void addChild(ITreeIdGetter var1);
}
