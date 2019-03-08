/*
  Fury, version 0.4.0. Copyright 2018-19 Jon Pretty, Propensive Ltd.

  The primary distribution site is: https://propensive.com/

  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
  in compliance with the License. You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required  by applicable  law or  agreed to  in writing,  software  distributed  under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express  or  implied.  See  the  License for  the specific  language  governing  permissions and
  limitations under the License.
 */
package fury

import scala.util._

object Pom {

  private def toXml(dependency: Binary): String = {
    s"""        <dependency>
       |            <groupId>${dependency.group}</groupId>
       |            <artifactId>${dependency.artifact}</artifactId>
       |            <version>${dependency.version}</version>
       |            <scope>compile</scope>
       |        </dependency>
       |""".stripMargin
  }

  def file(file: Path, ref: ModuleRef, dependencies: Set[Binary]): Try[Path] = {
    //TODO what should be treated as group id and version number?
    val content: String =
      s"""<project xmlns="http://maven.apache.org/POM/4.0.0"
         |         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         |         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
         |    <!-- Created by Fury ${Version.current} -->
         |    <modelVersion>4.0.0</modelVersion>
         |    <groupId>${ref.projectId.key}</groupId>
         |    <artifactId>${ref.projectId.key}</artifactId>
         |    <version>0.0.1-SNAPSHOT</version>
         |    <packaging>jar</packaging>
         |    <dependencies>
         |${dependencies.map(toXml).mkString("")}
         |    </dependencies>
         |</project>
       """.stripMargin

    file.writeSync(content).map { _ =>
      file
    }
  }

}