package com.natewilliford.fixer.objects;

import org.json.JSONObject;

interface Jsonizable {
    JSONObject toJson();
}
