# ShorteningUrl Project 실행방법
> Linux Java 설치

* linux> java -version (자바설치 유뮤 확인)
1. java 버전 확인되었다면  
    1. linux> which java (자바 설치 경로확인 및 복사)

2. java 설치가 필요한 경우
    1. linux> sudo apt-get install openjdk-8-jre
    2. linux> sudo apt-get install openjdk-8-jdk
    3. linux> which java (자바 설치 경로확인 및 복사)

> git source 다운로드
* git clone https://github.com/100sg/shortening_url.git
    
> server 구동을 위한 shellscript 실행
* shortening_url> ./start.sh {java_path} 로 shellscript를 실행한다. (아래 실행 내용)
    1. h2 DB 구동
    2. 소스 빌드
    3. project jar 파일 구동 
 
   




