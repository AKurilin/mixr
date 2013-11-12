# `mixr`

A Clojure library wrapping the Mixpanel HTTP REST API.

## Installation

`mixr` is available as a maven artifact from [Clojars](https://clojars.org/mixr)

```clojure
[mixr "0.0.1"]
```
## License

## Usage

The main functionality is provided by the `mixr.core` namespace.

Require it in the REPL:

```clojure
(require '[mixr :as mixr])
```

Require it in your application:

```clojure
(ns my-app.core
  (:require [mixr :as mixr]))
```

The client supports basic Mixpanel functionality such as `event`,
`update` and `increment`.

Copyright © 2013 Alexandr Kurilin

Distributed under the [MIT License (MIT)](http://opensource.org/licenses/MIT)
