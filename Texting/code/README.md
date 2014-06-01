# Collect - with mods from 2014 hack4co

Remove barriers to experimenting with new data sources.

  * [**What**](#what) are the intentions of this project?
  * [**Where**](#where) will I use it?
  * [**How**](#how) can I get started?
    * [**Setup**](#how-openshift) on Openshift
    * [**Configure**](#how-configure) data sources
    * [**Access**](#how-access) data sources
    * [**Ingest**](#how-ingest) data sources
    * [**Query**](#how-query) ingested data
    * [**Webhook**](#how-webhook) integration 

__Disclaimer__: This project is MIT licensed. Use at your own risk.

## What? <a name="what"></a>

A non-intimidating, personal sandbox with the following features.

  1. Central access to data sources through [**Mozilla Persona**](http://www.mozilla.org/en-US/persona/)
  2. Simple configuration of new data sources
    * JSON
  3. Eliminate the burden of auth
    * Declaration driven
    * Support common methods
      * OAuth 1.0
      * OAuth 2.0
      * API Key 
  4. Expose API endpoints through a common interface
    * Convention driven
    * Read only (GET)
  5. Support scriptable, composite endpoints
    * Javascript
  6. Provide a short-lived datastore 
    * Personal 
    * Ingest data on request
    * Query ingested data on request
    * Purge on a schedule

## Where? <a name="where"></a>

Personal setup on the [**OpenShift**](https://www.openshift.com) PAAS 

## How? <a name="how"></a>

### Setup Application on OpenShift <a name="how-openshift"></a>

  1. Make a fork of this repository
  2. Get [**prepared**](https://www.openshift.com/blogs/using-rhc-to-manage-paas-apps) on OpenShift
  3. Create an application on OpenShift
  ~~~~~~~~~~
  rhc app create <APPNAME> nodejs-0.6 mongodb-2.2
  ~~~~~~~~~

  4. Pull your fork into this application
  ~~~~~~~~~
  cd <APPNAME>
  git remote add github -m master git@github.com:<YOUR-FORK-OF-THIS-REPO>.git
  git pull -s recursive -X theirs github master
  ~~~~~~~~~

  5. Change references from proxy on dataupco to match your app and domain
  ~~~~~~~~~
  vi common-config.js // change db and host name
  git commit -am "changed proxy-dataupco references"
  ~~~~~~~~~

  6. Create and switch to a branch that __must remain private__
  ~~~~~~~~~~
  git branch secret
  git checkout secret
  ~~~~~~~~~~

  7. Edit config of secret for encryption of credentials
  ~~~~~~~~~~~~~
  vi storage-config.js
  ~~~~~~~~~~~~~

  8. Edit config and credentials for your data sources. See how [**here**](#how-configure).
  ~~~~~~~~~~~
  vi passport-config.js
  git commit -am "Edited accounts. KEEP SECRET."
  ~~~~~~~~~~~

  9. Deploy secret branch to openshift
  ~~~~~~~~~~~~~
  git push origin secret:master
  ~~~~~~~~~~~~~

### Configure Data Sources <a name="how-configure"></a>

  1. Configure applications at data source provider as applicable
    * Creation applications to use
    * Provide callback url for Oauth 
  2. See examples in passport-config.js for each auth type

### Access Data Sources <a name="how-access"></a>

  1. Login with Persona at `https://<YOUR-APP>.rhcloud.com`
  2. Use form to authorize data source at `/auth/{dataSource}` __(OAuth Only)__
  3. Access default path at `https://<YOUR-APP>.rhcloud.com/api/{dataSource}/`
  4. Access any path at `https://<YOUR-APP>.rhcloud.com/api/{dataSource}/any/valid/get/path`

### Ingest Data Sources <a name="how-ingest"></a>

  1. POST to `https://<YOUR-APP>.rhcloud.com/data/{dataSource}/{any/valid/get/path}`

### Query Ingested Data <a name="how-query"></a>

  1. Experiment with parameters after `https://<YOUR-APP>.rhcloud.com/data`
  ~~~~~~~~~
  GET /data?_meta.api={dataSourceName}
  GET /data?_meta.api={dataSourceName}&{some.path.to.match}={value}
  ~~~~~~~~~

  2. Experiment with operators after the query parameter names
  ~~~~~~~~
  GET /data?_meta.api=`{dataSourceName}&{some.path.to.match}~{operator}={value}
  ~~~~~~~~

  | Operator | Description | Example |
  | -------- | ----------- | ------- |
  | ~eq | Equal (Default) | `name~eq=abc` |
  | ~ne | Not Equal | `name~ne=abc` |
  | ~gt | Greater Than | `name~gt=123` |
  | ~gte | Greater Than or Equal | `name~gte=123` |
  | ~lt | Less Than | `name~lt=123` |
  | ~lte | Less Than or Equal | `name~lte=123` |
  | ~re | Regular Expression | `name~re=[abc]+` |
  | ~rei | Case Insensitive Regular Expression | `name~rei=[abc]+` |
  | ~in | In list separated by ~ | `name~in=abc~cde~fgh` |
  | ~nin | Not In list separted by ~ | `name~nin=abc~cde~fgh` |

### Webhook integration <a name="how-webhook"></a>

  1. Create a handle to a webhook data source  
  ~~~~~~~~
  POST /hook/{dataSource}
  ~~~~~~~~

  2. Test the credentials for the webhook (Basic Auth)
  ~~~~~~~~
  GET /hook/{dataSource}/{_id}
  ~~~~~~~~

  3. Execute the webhook handle
  ~~~~~~~~
  POST /hook/{dataSource}/{_id}
  ~~~~~~~~

  4. Delete the webhook handle
  ~~~~~~~~
  DELETE /hook/{dataSource}/{_id}
  ~~~~~~~~

  5. View all handles for a webhook 
  ~~~~~~~~
  GET /hook/{dataSource}
  ~~~~~~~~
