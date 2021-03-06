/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pedrovgs.nox.shape;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class SpiralShapeTest extends BaseShapeTestCase {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Shape shape;

  @Override public Shape getShape(ShapeConfig shapeConfig) {
    return ShapeFactory.getSpiralShape(shapeConfig);
  }

  @Test public void shouldReturnTheMiddleOfTheViewAsPositionForJustOneElement() {
    ShapeConfig shapeConfig =
        givenAShapeConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getShape(shapeConfig);

    shape.calculate();

    float expectedLeft = (ANY_VIEW_WIDTH / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedLeft, shape.getXForItemAtPosition(0), DELTA);
    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedTop, shape.getYForItemAtPosition(0), DELTA);
  }

  @Test public void shouldConfigureElementsFollowingAnArchimedeanSpiral() {
    ShapeConfig shapeConfig =
        givenAShapeConfig(10, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getShape(shapeConfig);

    shape.calculate();

    assertElementsConformAnSpiral(shapeConfig);
  }

  private void assertElementsConformAnSpiral(ShapeConfig shapeConfig) {
    for (int i = 0; i < shapeConfig.getNumberOfElements(); i++) {
      float expectedLeft = getExpectedLeftAtPosition(i, shapeConfig);
      float expectedTop = getExpectedTopAtPosition(i, shapeConfig);
      assertEquals(expectedLeft, shape.getXForItemAtPosition(i), DELTA);
      assertEquals(expectedTop, shape.getYForItemAtPosition(i), DELTA);
    }
  }

  private float getExpectedLeftAtPosition(int i, ShapeConfig shapeConfig) {
    float angle = shapeConfig.getItemSize();
    float centerX = (shapeConfig.getViewWidth() / 2)
        - (shapeConfig.getItemSize() / 2)
        - (shapeConfig.getItemMargin() / 2);
    return (float) (centerX + (angle * i * Math.cos(i)));
  }

  private float getExpectedTopAtPosition(int i, ShapeConfig shapeConfig) {
    float angle = shapeConfig.getItemSize();
    float centerY = (shapeConfig.getViewHeight() / 2)
        - (shapeConfig.getItemSize() / 2)
        - (shapeConfig.getItemMargin() / 2);
    return (float) (centerY + (angle * i * Math.sin(i)));
  }
}
