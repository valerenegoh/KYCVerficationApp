# 50.003 Remote KYC (Provided By IBM)
Current Know-Your-Customer (KYC) processes typically require a Face-to-Face (F2F) verification whereby a human needs to verify the identity of a person (e.g. passport and NRIC) against their physical identity.
The objective of this project is to:
(i) design an application/software that allows a company to verify the identification of a person with confidence by leveraging on multiple cross verification factors to ensure that the identity document provided by the user does in fact belong to the user; and
(ii) ensure a convenient, fast and secure verification process for the user

## Contributors
- Tan Li Yang:          Database, Registration, Email/PW Verification, Cryptographic Verfication, SMS OTP Verification
- Tracy Yee Enying:     Report contents, NRIC Barcode Verification, Testing, Facial Recognition Verification, Fingerprint Verification
- Valerene Goh Ze Yi:   UI/UX & Integration, Facial Verification, 
- Chua Xiao Wei:        Video

## Features Implemented
Based on the companies' needs, we provide a range of verification solutions for user identification. The variety of verification/authenticaion methods for the first and second steps are versatile and can be implemented flexibly based on the requirements of the company.

For example, Company A may want to collect fewer data from the its employees while maintaining a safe and fast login process. So Company A may choose barcode verification of its employees' pass/card (1st step) followed by fingerprint verification. 

Another example, Company B may want foolproof authentication that its employees is really logging in and not an imposter. So Company B may choose barcode verification, followed by SMS OTP of the employee's registered phone number, followed by a timed Facial Recognition of the employee.

- User Registration (with Firebase Database)

1st step:
- Email/PW Authentication
- NRIC/QR Code Verification
- Security Verification (Using Elliptic-Curve Cryptography)   - can bypass second step
- Password Reset (Email OTP Verification)

2nd step
- OTP Verification
- Fingerprint Verification
- Facial Recognition

