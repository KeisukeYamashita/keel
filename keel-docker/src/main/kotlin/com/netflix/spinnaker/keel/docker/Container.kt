/*
 *
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.netflix.spinnaker.keel.docker

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = ContainerDeserializer::class)
sealed class Container(
  open val organization: String,
  open val image: String
) {
  fun repository() = "$organization/$image"
}

@JsonDeserialize(using = JsonDeserializer.None::class)
data class ContainerWithDigest(
  override val organization: String,
  override val image: String,
  val digest: String
) : Container(organization, image)

@JsonDeserialize(using = JsonDeserializer.None::class)
data class ContainerWithVersionedTag(
  override val organization: String,
  override val image: String,
  val tagVersionStrategy: TagVersionStrategy,
  val captureGroupRegex: String? = null
) : Container(organization, image)