/*
 *    Copyright 2021 Whilein
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package w.flow;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collector;

/**
 * @author whilein
 */
public interface FlowItems<T> {

    <R, A> @NotNull Flow<R> collect(@NotNull Collector<? super T, A, R> collector);

    @NotNull
    Flow<T> findFirst();

    <A> @NotNull Flow<A> mapFirst(
            @NotNull FlowMapper<T, @Nullable A> function
    );

    @NotNull
    IntFlow mapFirstToInt(
            @NotNull ToIntFlowMapper<T> function
    );

    <A> @NotNull FlowItems<A> map(
            @NotNull FlowMapper<T, @Nullable A> function
    );

    @NotNull FlowItems<T> forEachCounted(@NotNull FlowCountedLoop<T> loop);

    @NotNull FlowItems<T> forEach(@NotNull FlowConsumer<T> loop);

    @NotNull
    IntFlowItems mapToInt(
            @NotNull ToIntFlowMapper<T> mapper
    );

    @NotNull FlowItems<T> filter(
            @NotNull FlowFilter<T> filter
    );

    void call();

    void callAsync();


}
