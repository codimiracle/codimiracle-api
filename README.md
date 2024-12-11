# Codimiracle Api
For fun, for life, encapsulating strange APIs.

## Structure
* codimiracle-api-dependencies

  using for manage dependencies.
* codimiracle-api-parent

  using for manage api module.
## API
### ocr-api
  Optical Character Recognition API
### mubu-api
  Mubu API
  retrieves mubu document by shareId (or with password).
```java
MubuConfig config = new MubuConfig();
config.setBaseUrl("https://api2.mubu.com/v3");
MubuDomain mubuDomain = new DefaultMubuDomain(config);

DocumentQuery query = new DocumentQuery();
query.setShareId("2w3AYxikYlE");
//or with password
//query.setPassword("123456");
Document document = mubuDomain.getDocument(query);
```
### douban-api
  Douban API
  retrieves douban book by subject id, test only

## Installation
### Manual
  1. clone the repo
  2. compile with maven
  3. `mvn install` to your local repo
  4. import in your project
### Maven
  (who care.)
### Gradle
(TODO)
## Licence
MIT

