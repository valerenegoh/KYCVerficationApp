# 50.003 Remote KYC (Provided By IBM)

[logo.pptx](https://github.com/ValereneGoh/KYCVerficationApp/files/1921882/logo.pptx)

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

<img width="630" alt="use cases" src="https://user-images.githubusercontent.com/23626462/38902956-52908c98-42d5-11e8-892c-bd36afa6bea4.PNG">

![one](https://user-images.githubusercontent.com/23626462/38902868-f4586fec-42d4-11e8-9a03-8cf155fa0356.png)

<img width="432" alt="two" src="https://user-images.githubusercontent.com/23626462/38902875-00035a46-42d5-11e8-9f52-bc61fcd12ae2.PNG">

<img width="445" alt="three" src="https://user-images.githubusercontent.com/23626462/38902876-00dabff4-42d5-11e8-851b-c4de6ccc85d3.PNG">

<img width="434" alt="four" src="https://user-images.githubusercontent.com/23626462/38902888-08f4bb36-42d5-11e8-870b-9cefc9907d50.PNG">

<img width="436" alt="five" src="https://user-images.githubusercontent.com/23626462/38902890-09eeb140-42d5-11e8-9bdf-c74de5bcdf72.PNG">

<img width="433" alt="six" src="https://user-images.githubusercontent.com/23626462/38902891-0d6f037e-42d5-11e8-942b-7d844c7feba0.PNG">

<img width="446" alt="seven" src="https://user-images.githubusercontent.com/23626462/38902905-1b06db38-42d5-11e8-936a-1b41ddd73592.PNG">

<img width="443" alt="nine" src="https://user-images.githubusercontent.com/23626462/38902916-281f212c-42d5-11e8-9d8f-19716c769a86.PNG">

<img width="438" alt="elevn" src="https://user-images.githubusercontent.com/23626462/38902932-375faa62-42d5-11e8-8c8e-752ed31d1f40.PNG">

<img width="429" alt="twelve" src="https://user-images.githubusercontent.com/23626462/38902934-392c198e-42d5-11e8-9c1d-
cb6e67a7f0c6.PNG">

<img width="436" alt="thirteen" src="https://user-images.githubusercontent.com/23626462/38902936-3ac358a2-42d5-11e8-8173-
4056453da7f5.PNG">

