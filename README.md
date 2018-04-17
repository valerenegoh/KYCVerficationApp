# 50.003 Remote KYC (Provided By IBM)
![logoo2](https://user-images.githubusercontent.com/23626462/38903065-c2f91cc0-42d5-11e8-8012-dd33eba8baa2.png)
Current Know-Your-Customer (KYC) processes typically require a Face-to-Face (F2F) verification whereby a human needs to verify the identity of a person (e.g. passport and NRIC) against their physical identity.
The objective of this project is to:
*(i) design an application/software that allows a company to verify the identification of a person with confidence by leveraging on multiple cross verification factors to ensure that the identity document provided by the user does in fact belong to the user; and
*(ii) ensure a convenient, fast and secure verification process for the user

## Contributors
- Tan Li Yang:          Database, Registration, Email/PW Verification, Cryptographic Verfication, SMS OTP Verification, Unit Testing
- Tracy Yee Enying:     Report contents, NRIC Barcode Verification, System & Robustness Testing
- Valerene Goh Ze Yi:   UI/UX & Integration, Facial Verification, Facial Recognition Verification, Fingerprint Verification, Unit Testing
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

<img width="630" alt="use cases" src="https://user-images.githubusercontent.com/23626462/38902956-52908c98-42d5-11e8-892c-bd36afa6bea4.PNG">

![one](https://user-images.githubusercontent.com/23626462/38902868-f4586fec-42d4-11e8-9a03-8cf155fa0356.png)
___
<img width="432" alt="two" src="https://user-images.githubusercontent.com/23626462/38902875-00035a46-42d5-11e8-9f52-bc61fcd12ae2.PNG">
___
<img width="445" alt="three" src="https://user-images.githubusercontent.com/23626462/38902876-00dabff4-42d5-11e8-851b-c4de6ccc85d3.PNG">
___
<img width="434" alt="four" src="https://user-images.githubusercontent.com/23626462/38902888-08f4bb36-42d5-11e8-870b-9cefc9907d50.PNG">
___
<img width="436" alt="five" src="https://user-images.githubusercontent.com/23626462/38902890-09eeb140-42d5-11e8-9bdf-c74de5bcdf72.PNG">
___
<img width="433" alt="six" src="https://user-images.githubusercontent.com/23626462/38902891-0d6f037e-42d5-11e8-942b-7d844c7feba0.PNG">
___
<img width="446" alt="seven" src="https://user-images.githubusercontent.com/23626462/38902905-1b06db38-42d5-11e8-936a-1b41ddd73592.PNG">
___
<img width="443" alt="nine" src="https://user-images.githubusercontent.com/23626462/38902916-281f212c-42d5-11e8-9d8f-19716c769a86.PNG">

## Testing

<img width="438" alt="elevn" src="https://user-images.githubusercontent.com/23626462/38902932-375faa62-42d5-11e8-8c8e-752ed31d1f40.PNG">

<img width="429" alt="twelve" src="https://user-images.githubusercontent.com/23626462/38903072-c8b3d7d6-42d5-11e8-9b98-6cdc6305237f.PNG">

<img width="436" alt="thirteen" src="https://user-images.githubusercontent.com/23626462/38903544-f3451076-42d7-11e8-8454-8b241a25c953.PNG">

## Future Works

- Creating a wallet to help users store their Private Keys securely
  - Help users ensure that their private keys are not compromised

- Ensure Zero-Knowledge-Proof
  - Allow users to prove their identity without revealing private data for security and privacy purposes

- Allow companies to set different verification methods for accessing segments of the company website with different degrees of sensitivity
  -Balance between ease of access and security
