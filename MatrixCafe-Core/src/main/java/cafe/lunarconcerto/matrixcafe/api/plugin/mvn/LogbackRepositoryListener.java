/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional debugrmation
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package cafe.lunarconcerto.matrixcafe.api.plugin.mvn;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.RepositoryEvent;
import org.jetbrains.annotations.NotNull;

@Slf4j(topic = "MatrixCafe-MavenResolver")
public class LogbackRepositoryListener extends AbstractRepositoryListener {
    
    public LogbackRepositoryListener() {

    }

    public void artifactDescriptorInvalid(@NotNull RepositoryEvent event) {
        log.warn("无法识别的工件描述符 =>" + event.getArtifact());
    }

    public void artifactDescriptorMissing(@NotNull RepositoryEvent event) {
        log.debug("缺失工件描述符 =>" + event.getArtifact());
    }

    public void artifactResolved(@NotNull RepositoryEvent event) {
        log.debug("正在解析工件 =>" + event.getArtifact());
    }

    public void artifactDownloading(@NotNull RepositoryEvent event) {
        log.debug("正在下载工件 =>" + event.getArtifact());
    }

    public void artifactDownloaded(@NotNull RepositoryEvent event) {
        log.debug("工件下载成功 =>" + event.getArtifact());
    }

    public void artifactResolving(@NotNull RepositoryEvent event) {
        log.debug("正在解析工件 =>" + event.getArtifact());
    }

}
