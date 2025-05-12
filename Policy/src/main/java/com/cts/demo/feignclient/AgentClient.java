package com.cts.demo.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.cts.demo.dto.Agent;
@FeignClient(name = "AGENT", path = "/agent")
public interface AgentClient {
   //Get Agent Data
   @GetMapping("/searchById/{aid}")
   public Agent getAgent(@PathVariable("aid") long agentId);
   //Assign Policy to Agent
   @PutMapping("/assignPoliciesToAgent/{pid}/{aid}/{pType}")
   public Agent assignPoliciesToAgent(@PathVariable("pid") long policyId,
                                      @PathVariable("aid") long agentId,
                                      @PathVariable("pType") String policyType);
}