# Amereta Generator

## V0.1.0
- models
- database (MySQL & PostgreSQL)
- liquibase

## How To Use
To generate an application with amereta:
- clone the repository https://github.com/amereta/amereta-generator.git
- build the project `mvn clean install`
- run the application `mvn spring-boot:run`
- make a `POST` request to `http://localhost:27015/api/generate` with your desired application description

### Sample Application Description
in yaml:

```yaml
---
applicationType: SPRING_BOOT
application:
  name: AwesomeApp
  package: com.mysite
  description: Amereta is Awesome!
  modules:
  - module: MODEL
    models:
    - type: ENUM
      name: CustomerLead
      fields:
      - name: site
      - name: email
    - type: DOMAIN
      name: Customer
      id: LONG
      authorizable: true
      fields:
      - name: name
        dataType: STRING
        defaultValue: Rich Customer
        nullable: false
      - name: phone
        dataType: STRING
        length: 11
        unique: true
        nullable: false
      - name: specialOffer
        dataType: STRING
        transient: true
      relations:
      - relationType: ONE_TO_ONE
        to: Wallet
        join: true
      - relationType: ONE_TO_MANY
        to: Address
    - type: DOMAIN
      name: Address
      fields:
      - name: zipcode
        dataType: STRING
        nullable: 'false'
      - name: street
        dataType: STRING
    - type: DOMAIN
      name: Wallet
      fields:
      - name: balance
        dataType: DOUBLE
        nullable: 'false'
    - type: DOMAIN
      name: Product
      fields:
      - name: name
        dataType: STRING
        defaultValue: Awesome Product
        nullable: false
      - name: description
        dataType: STRING
      relations:
      - relationType: MANY_TO_MANY
        to: Category
        join: true
    - type: DOMAIN
      name: Category
      fields:
      - name: name
        dataType: STRING
        nullable: 'false'
        unique: true
  - module: DB
    db:
      name: MYSQL
      username: myDbUsername
      password: myDbPassword
```

or in json:
```json
{
	"applicationType": "SPRING_BOOT",
	"application": {
		"name": "AwesomeApp",
		"package": "com.mysite",
		"description": "Amereta is Awesome!",
		"modules": [
			{
				"module": "MODEL",
				"models": [
					{
						"type": "ENUM",
						"name": "CustomerLead",
						"fields": [
							{ "name": "site" },
							{ "name": "email" }
						]
					},
					{
						"type": "DOMAIN",
						"name": "Customer",
						"id": "LONG",
						"authorizable": true,
						"fields": [
							{
								"name": "name",
								"dataType": "STRING",
								"defaultValue": "Rich Customer",
								"nullable": false
							},
							{
								"name": "phone",
								"dataType": "STRING",
								"length": 11,
								"unique": true,
								"nullable": false
							},
							{
								"name": "specialOffer",
								"dataType": "STRING",
								"transient": true
							}
						],
						"relations": [
							{
								"relationType" : "ONE_TO_ONE",
								"to": "Wallet"
							},
							{
								"relationType" : "ONE_TO_MANY",
								"to": "Address"
							}
						]
					},
					{
						"type": "DOMAIN",
						"name": "Address",
						"fields": [
							{
								"name": "zipcode",
								"dataType": "STRING",
								"nullable": "false"
							},
							{
								"name": "street",
								"dataType": "STRING"
							}
						]	
					},
					{
						"type": "DOMAIN",
						"name": "Wallet",
						"fields": [
							{
								"name": "balance",
								"dataType": "DOUBLE",
								"nullable": "false"
							}
						]
					},
					{
						"type": "DOMAIN",
						"name": "Product",
						"fields": [
							{
								"name": "name",
								"dataType": "STRING",
								"defaultValue": "Awesome Product",
								"nullable": false
							},
							{
								"name": "description",
								"dataType": "STRING"
							}
						],
						"relations": [
							{
								"relationType" : "MANY_TO_MANY",
								"to": "Category"
							}
						]
					},
					{
						"type": "DOMAIN",
						"name": "Category",
						"fields": [
							{
								"name": "name",
								"dataType": "STRING",
								"nullable": "false",
								"unique": true
							}
						]
					}
				]
			},
			{
				"module": "DB",
				"db": {
					"name": "MYSQL",
					"username": "myDbUsername",
					"password": "myDbPassword"
				}
			}
		]
	}
}
```
