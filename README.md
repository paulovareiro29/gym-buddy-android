<p align="center">
<img src="https://i.ibb.co/xC6fx3k/Gym-Buddy-logo.png" width="400" alt="Gym Buddy Logo"/>
</p>

<p align="center">
This project has been developed as part of the android development university class, with the objective of creating a full-stack mobile application. 
<p>

## Stack

- Kotlin
- Retrofit
- Room

## Getting Started

Before starting, this project expects Android Studio and all its dependencies to be installed.

### Run

To run the project locally:
- First you need to have the gym-buddy-api repository running.
- Go to the file `build.gradle.kts` present in the `api` module.
- Edit on `Default Config` the variable with name `API_URL`, with your `local IP Address + API Port`. e.g: `http://192.168.1.3:3000`

## Setup

### Environments

- We have three environment branches, `develop`, `staging` and `master`.
- `develop` is where all development happens. Every new feature, bug fix, or enhancement starts in a feature branch based off `develop`. Once completed, it gets merged back into `develop` always using `squash and merge`.
- `staging` mirrors a pre-production environment. Once features are tested and ready in `develop`, they are merged into `staging` for further testing in an environment that closely resembles production.
- `master` represents the production environment. Code in `master` is always deployed and reflects what's currently live. Merges into `master` happen from the `staging` branch after thorough testing.
- `hotfixes` are direct fixes to staging and master (if needed) that are made through hotfix branches. These are critical fixes that can't go through the usual process due to urgency. They should be merged back into both develop and staging to ensure consistency.

<pre>
[Feature branches]        [Develop]       [Staging]       [Master]
       |                       |               |               |
       |--- Feature A -------->|               |               |
       |                       |               |               |
       |--- Feature B -------->|               |               |
       |                       |--- Merge ---> |               |
       |                       |               |               |
       |                       |               |--- Deploy --> |
       |                       |               |               |
       |                       |<-- Hotfix ----|<-- Hotfix ----|
       |                       |               |               |
       |--- Feature C -------->|               |               |
       |                       |               |               |
       |                       |--- Merge ---> |               |
       |                       |               |               |
       |                       |               |--- Deploy --> |
</pre>