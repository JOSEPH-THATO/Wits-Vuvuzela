package com.example.wits_vuvuzela_app;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

//import static org.junit.Assert.assertNotNull;

public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> registerActivityTestRule = new ActivityTestRule<Register>(Register.class);
    public  Register register =null;

    @Before
    public void setUp() throws Exception {
        register = registerActivityTestRule.getActivity();
    }

    @Test
    public void MainTestLaunch(){

        View LogoImg = register.findViewById(R.id.imageView2);
        assertNotNull(LogoImg);

        View FirstNameET = register.findViewById(R.id.EdtxtFirstNameReg);
        assertNotNull(FirstNameET);

        View LastNameET = register.findViewById(R.id.EdtxtLastNameReg);
        assertNotNull(LastNameET);

        View UsernameET = register.findViewById(R.id.EdtxtUsernameReg);
        assertNotNull(UsernameET);

        View EmailET = register.findViewById(R.id.EdtxtEmailAddressReg);
        assertNotNull(EmailET);

        View PasswordET = register.findViewById(R.id.EdtxtPasswordReg);
        assertNotNull(PasswordET);

        View PasswordConfirmationET = register.findViewById(R.id.EdtxtPasswordConfirmationReg);
        assertNotNull(PasswordConfirmationET);

        View RegisterBtn = register.findViewById(R.id.BtnRegisterReg);
        assertNotNull(RegisterBtn);

        View YesAccountTV = register.findViewById(R.id.textView3);
        assertNotNull(YesAccountTV);

        View LoginTV = register.findViewById(R.id.txtViewLoginReg);
        assertNotNull(LoginTV);

    }
    @After
    public void tearDown() throws Exception {
        register=null;

    }
}