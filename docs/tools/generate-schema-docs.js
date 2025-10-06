const fs = require('fs');
const path = require('path');
const JsonSchemaStaticDocs = require('./vendor/json-schema-static-docs');

(async () => {
    const inputPath = path.resolve(__dirname, '../../cjson-schema-0/schemas');
    const outputPath = path.resolve(__dirname, '../cjson-dev/modules/reference/pages/schema-docs');

    console.log('🔎 Using input path:', inputPath);
    console.log('📂 Contents of inputPath:');
    try {
        const files = fs.readdirSync(inputPath);
        files.forEach(file => {
            console.log('  -', file);
        });
    } catch (err) {
        console.error('❌ Error reading inputPath:', err.message);
    }

    const generator = new JsonSchemaStaticDocs({
        inputPath: inputPath,
        outputPath: outputPath,
        jsonSchemaVersion: 'https://json-schema.org/draft/2020-12/schema',
        ajvOptions: { allowUnionTypes: true, strict: false },
        createIndex: true,
        indexTitle: 'CJSON Schemas',
        inputFileGlob: '**/*.schema.json'
    });

    try {
        await generator.generate();
        console.log('✅ JSON Schema docs generated at:', outputPath);
    } catch (err) {
        console.error('❌ Error generating docs:', err);
        process.exit(1);
    }
})();
