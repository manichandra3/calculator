# Jenkins CI/CD Setup for Calculator Project

This document provides step-by-step instructions to set up a Jenkins pipeline that **automatically pulls, compiles, tests, builds, and executes** the Calculator project whenever code is pushed to GitHub.

---

## Prerequisites

| Requirement       | Details                                    |
|-------------------|--------------------------------------------|
| Jenkins           | Version 2.387+ (LTS recommended)          |
| Java JDK          | JDK 17 installed on the Jenkins server     |
| Maven             | Maven 3.9+ installed on the Jenkins server |
| GitHub repository | `https://github.com/manichandra3/calculator.git` |

---

## Step 1: Install Jenkins

### On Ubuntu/Debian

```bash
# Add Jenkins repo key and source
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null

echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/" | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

# Install Jenkins
sudo apt update
sudo apt install jenkins -y

# Start Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

### On Docker (Alternative)

```bash
docker run -d \
  --name jenkins \
  -p 8080:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts-jdk17
```

Access Jenkins at `http://localhost:8080` and complete the initial setup wizard.

---

## Step 2: Install Required Jenkins Plugins

Navigate to **Manage Jenkins > Plugins > Available Plugins** and install:

1. **Pipeline** (usually pre-installed)
2. **Git plugin** (usually pre-installed)
3. **GitHub Integration Plugin**
4. **Maven Integration Plugin**
5. **JUnit Plugin** (for test reports)

Click **Install** and restart Jenkins when prompted.

---

## Step 3: Configure JDK and Maven in Jenkins

### 3.1 Configure JDK

1. Go to **Manage Jenkins > Tools**
2. Scroll to **JDK installations**
3. Click **Add JDK**
4. Set **Name** to: `JDK17`
5. Either:
   - Check **Install automatically** and select JDK 17 from the list, OR
   - Uncheck it and set **JAVA_HOME** to your JDK 17 installation path (e.g., `/usr/lib/jvm/java-17-openjdk-amd64`)
6. Click **Save**

### 3.2 Configure Maven

1. Go to **Manage Jenkins > Tools**
2. Scroll to **Maven installations**
3. Click **Add Maven**
4. Set **Name** to: `Maven`
5. Either:
   - Check **Install automatically** and select version 3.9.6+, OR
   - Uncheck it and set **MAVEN_HOME** to your Maven installation path (e.g., `/usr/share/maven`)
6. Click **Save**

> **IMPORTANT:** The names `JDK17` and `Maven` must match exactly with the names in the `Jenkinsfile` (`tools` section).

---

## Step 4: Create the Jenkins Pipeline Job

1. From the Jenkins dashboard, click **New Item**
2. Enter the name: `calculator-pipeline`
3. Select **Pipeline** as the project type
4. Click **OK**

---

## Step 5: Configure the Pipeline

### 5.1 General Settings

- Optionally add a description: `CI/CD pipeline for Calculator Java project`
- Check **GitHub project** and enter the URL:
  ```
  https://github.com/manichandra3/calculator
  ```

### 5.2 Build Triggers

Check **GitHub hook trigger for GITScm polling** — this enables automatic builds when code is pushed to GitHub.

### 5.3 Pipeline Definition

1. Set **Definition** to: `Pipeline script from SCM`
2. Set **SCM** to: `Git`
3. Set **Repository URL** to:
   ```
   https://github.com/manichandra3/calculator.git
   ```
4. Set **Branch Specifier** to: `*/main`
5. Set **Script Path** to: `Jenkinsfile`
6. Click **Save**

---

## Step 6: Set Up GitHub Webhook (Auto-trigger on Push)

This ensures Jenkins **immediately** builds when code is pushed to GitHub.

### 6.1 Get your Jenkins URL

Your Jenkins must be accessible from the internet. If running locally, use a tool like [ngrok](https://ngrok.com):

```bash
ngrok http 8080
```

Note the forwarding URL (e.g., `https://abc123.ngrok.io`).

### 6.2 Create the Webhook in GitHub

1. Go to your GitHub repository: `https://github.com/manichandra3/calculator`
2. Navigate to **Settings > Webhooks > Add webhook**
3. Configure:
   - **Payload URL**: `https://<your-jenkins-url>/github-webhook/`
   - **Content type**: `application/json`
   - **Which events?**: Select **Just the push event**
   - **Active**: Checked
4. Click **Add webhook**

GitHub will send a test ping. Verify it shows a green checkmark.

---

## Step 7: Test the Pipeline

### Option A: Trigger Manually

1. Go to the `calculator-pipeline` job in Jenkins
2. Click **Build Now**
3. Click the build number to view progress
4. Click **Console Output** to see the full log

### Option B: Trigger via Git Push

```bash
# Make any change to the code
git add .
git commit -m "Trigger Jenkins build"
git push origin main
```

Jenkins will automatically start the pipeline within seconds.

---

## Step 8: Verify the Pipeline Stages

The pipeline executes these stages in order:

| Stage      | What it does                                      |
|------------|---------------------------------------------------|
| Checkout   | Pulls latest code from GitHub                     |
| Compile    | Runs `mvn compile` to compile all Java sources    |
| Test       | Runs `mvn test` to execute JUnit 5 tests          |
| Build      | Runs `mvn package` to create the JAR artifact     |
| Execute    | Runs `java -jar target/calculator-1.0-SNAPSHOT.jar` |

After a successful build, you should see output like:

```
============================
   Calculator Application   
============================

Operands: a = 20.0, b = 5.0
----------------------------
Addition       : 20.0 + 5.0 = 25.0
Subtraction    : 20.0 - 5.0 = 15.0
Multiplication : 20.0 * 5.0 = 100.0
Division       : 20.0 / 5.0 = 4.0
============================
```

---

## Viewing Test Reports

After each build, Jenkins publishes JUnit test results:

1. Go to the build page
2. Click **Test Result** in the left sidebar
3. View individual test pass/fail status

---

## Troubleshooting

| Problem                             | Solution                                                            |
|--------------------------------------|---------------------------------------------------------------------|
| `mvn: command not found`            | Ensure Maven is configured in **Manage Jenkins > Tools**           |
| `JAVA_HOME not set`                 | Configure JDK in **Manage Jenkins > Tools**                        |
| Webhook not triggering              | Verify Jenkins URL is publicly accessible and webhook URL ends with `/github-webhook/` |
| Tool name mismatch                  | Ensure JDK name is `JDK17` and Maven name is `Maven` in Jenkins Tools config |
| Permission denied                   | Ensure Jenkins user has execute permission on Maven and Java        |
| Tests failing                       | Check **Console Output** for detailed test failure messages         |

---

## Project Structure

```
calculator/
├── Jenkinsfile                              # Pipeline definition
├── pom.xml                                  # Maven build configuration
├── src/
│   ├── main/java/com/calculator/
│   │   ├── Calculator.java                  # Core calculator logic
│   │   └── CalculatorMain.java              # Application entry point
│   └── test/java/com/calculator/
│       └── CalculatorTest.java              # JUnit 5 tests
├── .github/workflows/
│   └── ci.yml                               # GitHub Actions CI (alternative to Jenkins)
└── jenkins.md                               # This file
```

---

## Notes

- The **GitHub Actions** workflow (`.github/workflows/ci.yml`) serves as an alternative/complement to Jenkins. It runs automatically on every push to GitHub without any additional server setup.
- The **Jenkinsfile** is the primary CI/CD definition and requires a Jenkins server to be set up as described above.
- Both CI systems perform the same steps: **checkout -> compile -> test -> build -> execute**.
