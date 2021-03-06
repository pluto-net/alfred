package network.pluto.alfred.services;

import network.pluto.alfred.components.BlockchainClient;
import network.pluto.bibliotheca.repositories.MemberRepository;
import network.pluto.bibliotheca.repositories.WalletRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletServiceTest {

    @MockBean
    BlockchainClient blockchainClient;

    @MockBean
    WalletRepository walletRepository;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    WalletService walletService;

    @Test
    public void testCreateWallet_NormalUser_ShouldReturnNormalResult() throws Exception {
//        Wallet wallet = new Wallet();
//        wallet.setAddress("mywalletaddress");
//        given(walletRepository.save((Wallet) anyObject())).willReturn(wallet);
//        given(blockchainClient.sendCreateWalletReq(anyObject())).willReturn("mywalletaddress");
//
//        Member member = new Member();
//        member.setMemberId(1L);
//        member.setPassword("asdf");
//
//        assertThat(this.walletService.createWallet(member)).hasFieldOrPropertyWithValue("address", "mywalletaddress");
    }
}
