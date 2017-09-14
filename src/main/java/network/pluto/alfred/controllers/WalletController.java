package network.pluto.alfred.controllers;

import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import network.pluto.alfred.services.MemberService;
import network.pluto.alfred.services.WalletService;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final MemberService memberService;
    private final WalletService walletService;

    @Autowired
    public WalletController(MemberService memberService, WalletService walletService) {
        this.memberService = memberService;
        this.walletService = walletService;
    }

    @ApiOperation(value = "Create a wallet for the given member.", response = Wallet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a wallet."),
            @ApiResponse(code = 400, message = "You didn't send appropriate input data."),
            @ApiResponse(code = 500, message = "An error was occurred while creating a wallet.")
    })
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> createWallet(@RequestBody Member member) {

        Member currentMember = this.memberService.getMemberByIdAndPassword(member.getMemberId(), member.getPassword());
        if (currentMember == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ImmutableMap.of("message", "No member was matched with given information."));
        }

        if(currentMember.getWallet() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ImmutableMap.of("message", "The member already has a wallet."));
        }

        Wallet wallet = this.walletService.createWallet(currentMember);
        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        this.memberService.setWallet(currentMember, wallet);

        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }
}
