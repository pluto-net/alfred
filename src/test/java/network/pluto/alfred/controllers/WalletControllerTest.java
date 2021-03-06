package network.pluto.alfred.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.pluto.alfred.services.MemberService;
import network.pluto.alfred.services.WalletService;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @MockBean
    private WalletService walletService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateWallet_NormalWalletCreation_ShouldReturnNormalResult() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setPassword("asdf");

        Wallet result = new Wallet();
        result.setId(1L);
        result.setAddress("mynewwalletaddr");

        given(this.memberService.getMemberById(anyLong())).willReturn(member);

        this.mockMvc.perform(post("/wallet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(member)))
                .andExpect(status().isCreated())
                .andExpect(
                        content().json(
                                "{\"walletId\":1,\"address\":\"mynewwalletaddr\",\"createDate\":null,\"updateDate\":null}"));
    }

    @Test
    public void testCreateWallet_InvalidJsonRequestBody_ShouldReturnError() throws Exception {
        Member memberWithoutPassword = new Member();
        memberWithoutPassword.setId(1L);

        given(this.memberService.getMemberById(anyLong())).willReturn(null);

        this.mockMvc.perform(post("/wallet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(memberWithoutPassword)))
                .andExpect(status().isBadRequest());

        Member memberWithoutId = new Member();
        memberWithoutId.setPassword("asdf");

        given(this.memberService.getMemberById(anyLong())).willReturn(null);

        this.mockMvc.perform(post("/wallet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(memberWithoutId)))
                .andExpect(status().isBadRequest());
    }
}
