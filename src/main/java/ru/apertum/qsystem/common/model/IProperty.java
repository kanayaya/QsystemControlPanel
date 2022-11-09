package ru.apertum.qsystem.common.model;

import org.dom4j.Element;

public interface IProperty {
   String getName();

   Object getValue();

   @Deprecated
   Element getXML();

   @Deprecated
   Object getInstance();

   @Override
   String toString();

   Long getId();
}
