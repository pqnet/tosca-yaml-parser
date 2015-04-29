/*
 * Copyright 2015 Universita' di Pisa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package seaclouds.utils.toscamodel.examples;

import seaclouds.utils.toscamodel.INodeTemplate;
import seaclouds.utils.toscamodel.INodeType;
import seaclouds.utils.toscamodel.IToscaEnvironment;
import seaclouds.utils.toscamodel.Tosca;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by pq on 27/04/2015.
 */
public class Example {

    public static void main(String[] params) {
        IToscaEnvironment aam = Tosca.newEnvironment();
        IToscaEnvironment discoverer = Tosca.newEnvironment();
        InputStream stream = Example.class.getResourceAsStream("../../../../input/aam.yaml");
        aam.readFile(new InputStreamReader(stream));
        stream = Example.class.getResourceAsStream("../../../../input/amazon_c1_xlarge.yaml");
        discoverer.readFile(new InputStreamReader(stream));
        stream = Example.class.getResourceAsStream("../../../../input/platform_offerings_test.yaml");
        discoverer.readFile(new InputStreamReader(stream));
        //stream = Example.class.getResourceAsStream("\\input\\hp_cloud_serv.yaml");
        //discoverer.readFile(new InputStreamReader(stream),true);
        Matchmaker m = new Matchmaker(discoverer);
        Map<String, List<INodeType>> matches = m.Match(aam);
        System.out.println(matches);
        OptimizerExample o = new OptimizerExample();
        final List<IToscaEnvironment> plans = o.optimizeFullSearchCartesian(aam, matches);
        for (IToscaEnvironment plan : plans) {
            PrintWriter p = new PrintWriter(System.out);
            plan.writeFile(p);
            p.flush();
            //p.close();
        }


    }
}
