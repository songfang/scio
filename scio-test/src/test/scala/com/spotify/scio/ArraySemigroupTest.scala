/*
 * Copyright 2016 Spotify AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.scio

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

class ArraySemigroupTest extends FlatSpec with Matchers {

  val dimension = 10
  val sg = new ArraySemigroup[Double]
  def nextArray: Array[Double] = Array.fill(dimension)(Random.nextDouble)

  def plus(l: Array[Double], r: Array[Double]): Array[Double] = l.zip(r).map(p => p._1 + p._2)

  "ArraySemigroup" should "support plus" in {
    val l = nextArray
    val r = nextArray
    sg.plus(l, r) should equal (plus(l, r))
  }

  it should "support sumOption" in {
    sg.sumOption(Seq.empty[Array[Double]]) shouldBe None

    val a = nextArray
    sg.sumOption(Seq(a)).get should equal (a)

    val xs = (1 to 100).map(_ => nextArray)
    sg.sumOption(xs).get should equal (xs.reduce(plus))
  }

}